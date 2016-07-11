package webschedule;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A generalized table row. Actually, can be used to handle the outer
 * levels of any structure that is built from similar, nested
 * building blocks. Tables are just the most widely used thing of that 
 * kind.
 * @author Richard Kunze, opus 5 interaktive medien gmbh
 * @version $Id: DOMTableRow.java,v 1.4 2000/01/17 00:06:46 richard $
 */
public class DOMTableRow {

    /** Table row DOM node */
    private Element rowElement;

    /** Immediate parent of the cell template. */
    private Element cellParent = null;

    /** Next sibling of the cell template, indicates where new cells
        are inserted. If <code>null</code>, insert new cells as last
        child of <code>cellParent</code> */
    private Node cellAnchor = null;
       

    /**
     * Construct a new table row from a template. the node designed
     * by <code>rowTemplate</code> is cloned and, if
     * <code>generateNewRow</code> is <code>true</code>, the copy is
     * inserted into the document just before <code>rowTemplate</code>.
     * <p>
     * If the node <code>cellTemplate</code> 
     * is a subnode of <code>rowTemplate</code>, this node designates
     * the place where subsequent calls to <code>addCell()</code> will 
     * place the new table cells. <code>rowTemplate</code> itself is
     * omitted in cloning <code>cellTemplate</code>. If
     * <code>cellTemplate</code> is <code>null</code> or not present in
     * <code>rowTemplate</code>, new table cells will be added as last 
     * child of <code>rowTemplate</code>.
     * @param rowTemplate the template for the table row.
     * @param cellTemplate template for a table cell within this row.
     * @param generateNewRow Flag, tells wheter to insert a new row or not.
     * @exception IllegalArgumentException If
     * <code>generateNewRow</code> is <code>true</code>, but
     * <code>rowTemplate</code> does not have a prent node.
     * @see #addCell
     */
    public DOMTableRow(Element rowTemplate,
                       Element cellTemplate,
                       boolean generateNewRow) {
        rowElement = (Element)cloneRow(rowTemplate, cellTemplate);
        // Default to rowElement itself, if no cellTemplate is found in 
        // cloneRow()
        if (cellParent == null) cellParent = rowElement;

        // If this has a parent, insert the new row just before the template.
        Node rowParent = rowTemplate.getParentNode();
        if (generateNewRow) {
            try {
                rowParent.insertBefore(rowElement, rowTemplate);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException(
                    "generateNewRow may not be true for rowTemplates without parent node.");
            }
        }
    }

    /**
     * Construct a new table row from a template. The node designed
     * by <code>rowTemplate</code> is cloned and, if
     * <code>rowTemplate</code> has a parent node, the copy is 
     * inserted into the DOM just before <code>rowTemplate</code>.
     * <p>
     * If the node <code>cellTemplate</code> 
     * is a subnode of <code>rowTemplate</code>, this node designates
     * the place where subsequent calls to <code>addCell()</code> will 
     * place the new table cells. <code>rowTemplate</code> itself is
     * omitted in cloning <code>cellTemplate</code>. If
     * <code>cellTemplate</code> is <code>null</code> or not present in
     * <code>rowTemplate</code>, new table cells will be added as last 
     * child of <code>rowTemplate</code>.
     * @param rowTemplate the template for the table row.
     * @param cellTemplate template for a table cell within this row.
     * @see #addCell
     */
    public DOMTableRow(Element rowTemplate, Element cellTemplate) {
        this(rowTemplate, cellTemplate, rowTemplate.getParentNode() != null);
    }
    
    /**
     * Add a new table cell. <code>cellTemplate</code> is cloned and
     * the copy is inserted at the place designated by
     * <code>cellTemplate</code> in the constructor call.
     * <p>
     * It is up to the caller to make sure that
     * <code>cellTemplate</code> is of a node type that may be
     * inserted at this place.
     * @param cellTemplate  the template node for the new table cell.
     * @see #DOMTableRow
     */
    public void addCell(Element cellTemplate) {
        if (cellAnchor == null) {
            cellParent.appendChild(cellTemplate.cloneNode(true));
        } else {
            cellParent.insertBefore(cellTemplate.cloneNode(true),
                                    cellAnchor);
        }
    }

    /**
     * Get the table row DOM node.
     * @return the DOM node for the table row.
     */
    public Element getElement() { return rowElement; }

    /**
     * Recursively clone a DOM node. <code>cellTemplate</code> is
     * omitted in cloning, but its next sibling and its parent are
     * recorded in <code>cellAnchor</code> and
     * <code>cellParent</code>.
     * @param node the DOM node to clone
     * @param cellTemplate the template node designating the place to
     * add table cells
     * @return the newly cloned DOM node
     * @see #cellAnchor
     * @see #cellParent
     */
    private Node cloneRow(Node node, Element cellTemplate) {
        Node newNode = node.cloneNode(false);

        Node child = node.getFirstChild();
        boolean recordNode = false;
        while (child != null) {
            if (child != cellTemplate) {
                Node newChild = cloneRow(child, cellTemplate);
                if (recordNode) {
                    cellAnchor = newChild;
                    recordNode = false;
                }
                newNode.appendChild(newChild);
            } else {
                recordNode = true;
                cellParent = (Element)newNode;
            }
            child = child.getNextSibling();
        }
        return newNode;
    }
}

