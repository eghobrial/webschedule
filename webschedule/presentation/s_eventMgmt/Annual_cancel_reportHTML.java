/*
 ************************************
 * XMLC GENERATED CODE, DO NOT EDIT *
 ************************************
 */
package webschedule.presentation.s_eventMgmt;
import org.w3c.dom.*;
import org.enhydra.xml.xmlc.XMLCUtil;
import org.enhydra.xml.xmlc.XMLCError;
import org.enhydra.xml.xmlc.dom.XMLCDomFactory;

public class Annual_cancel_reportHTML extends org.enhydra.xml.xmlc.html.HTMLObjectImpl {
    /**
     * Field that is used to identify this as an XMLC
     * generated class.  Contains an reference to the
     * class object.
     */
    public static final Class XMLC_GENERATED_CLASS = Annual_cancel_reportHTML.class;

    /**
     * Field containing CLASSPATH relative name of the source file
     * that this class was generated from.
     */
    public static final String XMLC_SOURCE_FILE = "webschedule/presentation/s_eventMgmt/Annual_cancel_report.html";

    /**
     * Element name constant for year
     */
    public static final String NAME_year = "year";
    /**
     * Element name constant for smonth
     */
    public static final String NAME_smonth = "smonth";
    /**
     * Element name constant for Canellation
     */
    public static final String NAME_Canellation = "Canellation";
    /**
     * Element name constant for Submit
     */
    public static final String NAME_Submit = "Submit";
    /**
     * Element name constant for event
     */
    public static final String NAME_event = "event";
    /**
     * Element name constant for emonth
     */
    public static final String NAME_emonth = "emonth";
    /**
     * Get the element with id <CODE>ErrorText</CODE>.
     * @see org.w3c.dom.html.HTMLElement
     */
    public org.w3c.dom.html.HTMLElement getElementErrorText() {
        return $elementErrorText;
    }
    private org.w3c.dom.html.HTMLElement $elementErrorText;

    /**
     * Get the value of text child of element <CODE>ErrorText</CODE>.
     * @see org.w3c.dom.Text
     */
    public void setTextErrorText(String text) {
        XMLCUtil.getFirstText($elementErrorText).setData(text);
    }

    /**
     * Get the element with id <CODE>Smonth</CODE>.
     * @see org.w3c.dom.html.HTMLSelectElement
     */
    public org.w3c.dom.html.HTMLSelectElement getElementSmonth() {
        return $elementSmonth;
    }
    private org.w3c.dom.html.HTMLSelectElement $elementSmonth;

    /**
     * Get the element with id <CODE>EventValue</CODE>.
     * @see org.w3c.dom.html.HTMLInputElement
     */
    public org.w3c.dom.html.HTMLInputElement getElementEventValue() {
        return $elementEventValue;
    }
    private org.w3c.dom.html.HTMLInputElement $elementEventValue;

    /**
     * Get the element with id <CODE>year</CODE>.
     * @see org.w3c.dom.html.HTMLSelectElement
     */
    public org.w3c.dom.html.HTMLSelectElement getElementYear() {
        return $elementyear;
    }
    private org.w3c.dom.html.HTMLSelectElement $elementyear;

    /**
     * Get the element with id <CODE>Emonth</CODE>.
     * @see org.w3c.dom.html.HTMLSelectElement
     */
    public org.w3c.dom.html.HTMLSelectElement getElementEmonth() {
        return $elementEmonth;
    }
    private org.w3c.dom.html.HTMLSelectElement $elementEmonth;

    /**
     * Create document as a DOM and initialize accessor method fields.
     */
    public void buildDocument() {
        XMLCDomFactory domFactory = org.enhydra.xml.xmlc.dom.XMLCDomFactoryCache.getFactory("org.enhydra.xml.xmlc.dom.DefaultHTMLDomFactory");
        Document document = domFactory.createDocument(null, null);
        setDocument(document);

        domFactory.setErrorChecking(document, false);

        Node $node0, $node1, $node2, $node3, $node4, $node5, $node6, $node7, $node8, $node9, $node10, $node11;
        Element $elem0, $elem1, $elem2, $elem3, $elem4, $elem5, $elem6, $elem7, $elem8, $elem9, $elem10;
        Attr $attr0, $attr1, $attr2, $attr3, $attr4, $attr5, $attr6, $attr7, $attr8, $attr9, $attr10;

        $elem0 = document.getDocumentElement();
        $elem1 = document.createElement("HEAD");;
        $elem0.appendChild($elem1);
        
        $elem2 = document.createElement("TITLE");;
        $elem1.appendChild($elem2);
        
        $node3 = document.createTextNode("Annual/Semi Cancellation Annual Project Report");;
        $elem2.appendChild($node3);
        
        $elem2 = document.createElement("META");;
        $elem1.appendChild($elem2);
        
        $attr2 = document.createAttribute("content");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("text/html; charset=iso-8859-1");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("http-equiv");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("Content-Type");;
        $attr2.appendChild($node3);
        
        $elem2 = document.createElement("LINK");;
        $elem1.appendChild($elem2);
        
        $attr2 = document.createAttribute("href");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("../media/StyleSheet.css");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("rel");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("stylesheet");;
        $attr2.appendChild($node3);
        
        $elem1 = document.createElement("BODY");;
        $elem0.appendChild($elem1);
        
        $attr1 = document.createAttribute("bgcolor");
        $elem1.setAttributeNode($attr1);
        
        $node2 = document.createTextNode("#FFFFFF");;
        $attr1.appendChild($node2);
        
        $attr1 = document.createAttribute("text");
        $elem1.setAttributeNode($attr1);
        
        $node2 = document.createTextNode("#000000");;
        $attr1.appendChild($node2);
        
        $elem2 = document.createElement("TABLE");;
        $elem1.appendChild($elem2);
        
        $attr2 = document.createAttribute("border");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("0");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("cellpadding");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("0");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("cellspacing");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("0");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("mm:layoutgroup");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("true");;
        $attr2.appendChild($node3);
        
        $attr2 = document.createAttribute("width");
        $elem2.setAttributeNode($attr2);
        
        $node3 = document.createTextNode("1011");;
        $attr2.appendChild($node3);
        
        $elem3 = document.createElement("TR");;
        $elem2.appendChild($elem3);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("rowspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("3");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("width");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("19");;
        $attr4.appendChild($node5);
        
        $node5 = document.createTextNode("\u00a0");;
        $elem4.appendChild($node5);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("height");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("43");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("width");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("566");;
        $attr4.appendChild($node5);
        
        $elem5 = document.createElement("IMG");;
        $elem4.appendChild($elem5);
        
        $attr5 = document.createAttribute("height");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("43");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("src");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("../media/Hlogo.gif");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("width");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("213");;
        $attr5.appendChild($node6);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("width");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("214");;
        $attr4.appendChild($node5);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("width");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("212");;
        $attr4.appendChild($node5);
        
        $elem3 = document.createElement("TR");;
        $elem2.appendChild($elem3);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("colspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("2");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("height");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("16");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $elem5 = document.createElement("IMG");;
        $elem4.appendChild($elem5);
        
        $attr5 = document.createAttribute("height");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("14");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("src");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("../media/Hucsd.gif");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("width");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("600");;
        $attr5.appendChild($node6);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $elem3 = document.createElement("TR");;
        $elem2.appendChild($elem3);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("bgcolor");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("#006699");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("colspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("2");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("height");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("29");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $elem5 = document.createElement("H2");;
        $elem4.appendChild($elem5);
        
        $attr5 = document.createAttribute("align");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("center");;
        $attr5.appendChild($node6);
        
        $node6 = document.createTextNode("Cancellation Report");;
        $elem5.appendChild($node6);
        
        $node5 = document.createTextNode("fin");;
        $elem4.appendChild($node5);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $elem3 = document.createElement("TR");;
        $elem2.appendChild($elem3);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("rowspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("2");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $node5 = document.createTextNode("\u00a0");;
        $elem4.appendChild($node5);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("colspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("2");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("height");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("25");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $elem5 = document.createElement("P");;
        $elem4.appendChild($elem5);
        
        $elem6 = document.createElement("FONT");;
        $elem5.appendChild($elem6);
        
        $attr6 = document.createAttribute("color");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("red");;
        $attr6.appendChild($node7);
        
        $attr6 = document.createAttribute("face");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("");;
        $attr6.appendChild($node7);
        
        $attr6 = document.createAttribute("size");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("4");;
        $attr6.appendChild($node7);
        
        $elem7 = document.createElement("SPAN");;
        $elem6.appendChild($elem7);
        
        $elementErrorText = (org.w3c.dom.html.HTMLElement)$elem7;
        $attr7 = document.createAttribute("id");
        $elem7.setAttributeNode($attr7);
        
        $node8 = document.createTextNode("ErrorText");;
        $attr7.appendChild($node8);
        
        $node8 = document.createTextNode("Optional Error Text Goes Here");;
        $elem7.appendChild($node8);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $elem3 = document.createElement("TR");;
        $elem2.appendChild($elem3);
        
        $elem4 = document.createElement("TD");;
        $elem3.appendChild($elem4);
        
        $attr4 = document.createAttribute("colspan");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("3");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("height");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("293");;
        $attr4.appendChild($node5);
        
        $attr4 = document.createAttribute("valign");
        $elem4.setAttributeNode($attr4);
        
        $node5 = document.createTextNode("top");;
        $attr4.appendChild($node5);
        
        $elem5 = document.createElement("FORM");;
        $elem4.appendChild($elem5);
        
        $attr5 = document.createAttribute("action");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("Annual_cancel_report.po");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("method");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("post");;
        $attr5.appendChild($node6);
        
        $attr5 = document.createAttribute("name");
        $elem5.setAttributeNode($attr5);
        
        $node6 = document.createTextNode("Canellation");;
        $attr5.appendChild($node6);
        
        $elem6 = document.createElement("TABLE");;
        $elem5.appendChild($elem6);
        
        $attr6 = document.createAttribute("border");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("0");;
        $attr6.appendChild($node7);
        
        $attr6 = document.createAttribute("cellpadding");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("0");;
        $attr6.appendChild($node7);
        
        $attr6 = document.createAttribute("cellspacing");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("0");;
        $attr6.appendChild($node7);
        
        $attr6 = document.createAttribute("width");
        $elem6.setAttributeNode($attr6);
        
        $node7 = document.createTextNode("600");;
        $attr6.appendChild($node7);
        
        $elem7 = document.createElement("TR");;
        $elem6.appendChild($elem7);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("height");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("30");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("3");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("\u00a0");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("center");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("180");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("Starting Month");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("419");;
        $attr8.appendChild($node9);
        
        $elem9 = document.createElement("SELECT");;
        $elem8.appendChild($elem9);
        
        $elementSmonth = (org.w3c.dom.html.HTMLSelectElement)$elem9;
        $attr9 = document.createAttribute("id");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("Smonth");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("name");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("smonth");;
        $attr9.appendChild($node10);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("0");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Jan");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("1");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Feb");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("March");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("3");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("April");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("4");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("May");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("5");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("June");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("6");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("July");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("7");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("August");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("8");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Sept");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("9");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Oct");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("10");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Nov");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("11");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Dec");;
        $elem10.appendChild($node11);
        
        $elem7 = document.createElement("TR");;
        $elem6.appendChild($elem7);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("height");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("30");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("3");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("\u00a0");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("center");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("180");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("Ending Month");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("419");;
        $attr8.appendChild($node9);
        
        $elem9 = document.createElement("SELECT");;
        $elem8.appendChild($elem9);
        
        $elementEmonth = (org.w3c.dom.html.HTMLSelectElement)$elem9;
        $attr9 = document.createAttribute("id");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("Emonth");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("name");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("emonth");;
        $attr9.appendChild($node10);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("0");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Jan");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("1");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Feb");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("March");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("3");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("April");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("4");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("May");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("5");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("June");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("6");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("July");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("7");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("August");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("8");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Sept");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("9");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Oct");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("10");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Nov");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("11");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("Dec");;
        $elem10.appendChild($node11);
        
        $elem7 = document.createElement("TR");;
        $elem6.appendChild($elem7);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("height");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("29");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("3");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("\u00a0");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("center");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("180");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("Year");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $elem9 = document.createElement("SELECT");;
        $elem8.appendChild($elem9);
        
        $elementyear = (org.w3c.dom.html.HTMLSelectElement)$elem9;
        $attr9 = document.createAttribute("id");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("year");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("name");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("year");;
        $attr9.appendChild($node10);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2001");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2001");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2002");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2002");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2003");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2003");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("selected");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("");;
        $attr10.appendChild($node11);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2004");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2004");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2005");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2005");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2006");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2006");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2007");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2007");;
        $elem10.appendChild($node11);
        
        $elem10 = document.createElement("OPTION");;
        $elem9.appendChild($elem10);
        
        $attr10 = document.createAttribute("value");
        $elem10.setAttributeNode($attr10);
        
        $node11 = document.createTextNode("2008");;
        $attr10.appendChild($node11);
        
        $node11 = document.createTextNode("2008");;
        $elem10.appendChild($node11);
        
        $elem7 = document.createElement("TR");;
        $elem6.appendChild($elem7);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("height");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("51");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("3");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("\u00a0");;
        $elem8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("180");;
        $attr8.appendChild($node9);
        
        $elem9 = document.createElement("INPUT");;
        $elem8.appendChild($elem9);
        
        $elementEventValue = (org.w3c.dom.html.HTMLInputElement)$elem9;
        $attr9 = document.createAttribute("id");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("EventValue");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("name");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("event");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("type");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("hidden");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("value");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("Generate");;
        $attr9.appendChild($node10);
        
        $node9 = document.createTextNode(" ");;
        $elem8.appendChild($node9);
        
        $elem9 = document.createElement("INPUT");;
        $elem8.appendChild($elem9);
        
        $attr9 = document.createAttribute("name");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("Submit");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("type");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("submit");;
        $attr9.appendChild($node10);
        
        $attr9 = document.createAttribute("value");
        $elem9.setAttributeNode($attr9);
        
        $node10 = document.createTextNode("Generate Annual Report");;
        $attr9.appendChild($node10);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("valign");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("top");;
        $attr8.appendChild($node9);
        
        $node9 = document.createTextNode("\u00a0");;
        $elem8.appendChild($node9);
        
        $elem7 = document.createElement("TR");;
        $elem6.appendChild($elem7);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("height");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("40");;
        $attr8.appendChild($node9);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("3");;
        $attr8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        
        $attr8 = document.createAttribute("width");
        $elem8.setAttributeNode($attr8);
        
        $node9 = document.createTextNode("180");;
        $attr8.appendChild($node9);
        
        $elem8 = document.createElement("TD");;
        $elem7.appendChild($elem8);
        

        domFactory.setErrorChecking(document, true);

    }

    /**
     * Recursize function to do set access method fields from the DOM.
     * Missing ids have fields set to null.
     */
    protected void syncWithDocument(Node node) {
        if (node instanceof Element) {
            String id = ((Element)node).getAttribute("id");
            if (id.length() == 0) {
            } else if (id.equals("ErrorText")) {
                $elementErrorText = (org.w3c.dom.html.HTMLElement)node;
            } else if (id.equals("Smonth")) {
                $elementSmonth = (org.w3c.dom.html.HTMLSelectElement)node;
            } else if (id.equals("EventValue")) {
                $elementEventValue = (org.w3c.dom.html.HTMLInputElement)node;
            } else if (id.equals("year")) {
                $elementyear = (org.w3c.dom.html.HTMLSelectElement)node;
            } else if (id.equals("Emonth")) {
                $elementEmonth = (org.w3c.dom.html.HTMLSelectElement)node;
            }
        }
    }

    /**
     * Default constructor.
     */
    public Annual_cancel_reportHTML() {
        buildDocument();
    }

    /**
     * Constructor with optional building of the DOM. 
     *
     * @param buildDOM If false, the DOM will not be built until
     * buildDocument() is called by the derived class.  If true, 
     * the DOM is built immediatly.
     */
    public Annual_cancel_reportHTML(boolean buildDOM) {
        if (buildDOM) {
            buildDocument();
        }
    }

    /**
     * Copy constructor.
     * @param src The document to clone.
     */
    public Annual_cancel_reportHTML(Annual_cancel_reportHTML src) {
        setDocument((Document)src.getDocument().cloneNode(true));
        syncAccessMethods();
    }

    /**
      * Clone the document.
      * @param deep Must be true, only deep clone is supported.
      */
    public Node cloneNode(boolean deep) {
        cloneDeepCheck(deep);
        return new Annual_cancel_reportHTML(this);
    }

}
