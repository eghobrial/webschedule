package webschedule.presentation;

import org.enhydra.xml.xmlc.html.HTMLObjectImpl;

/**
 * Base Class für alle XMLC Views.
 * 
 * Einzige Funktion bisher: implementierung der geänderten Methoden
 * isSupported, die von neueren XML DOM Implementierungen erwartet,
 * aber von XMLC Version 2.1 derzeit nicht generiert wird.
 * 
 * Dies führte bei Umstieg auf JKD 1.4 zu Problemen, die mit dieser
 * Basisklasse behoben werden.
 *
 * @author      Manfred P. Regele
 * @author      [author]  @(#)
 * @version     [crtime] [version] / [environment] [state] [package] [build] @(#)
 * @see                 <a href="www.enhydra.org">Enhydra (xmlc home)</a>
 * @since       2002-03-20 (V1.1)
 *
 */
public abstract class HTMLVbhObjectImpl extends HTMLObjectImpl {

/**
  * Tests whether a specific DOM feature is implemented by this node.
  *
  * @param feature The name of the feature to test.
  * @param version The version number of the feature. In Level 2, version 1,
  * this is the string "2.0". If not specified, supporting any version of
the
  * feature will cause the method to return true.
  * @return true if the feature is supported by this node, else false.
  */
 public boolean isSupported(String feature, String version) {
   return supports(feature, version);
 }

}
