package xml_creator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLCreator {
	Document doc;
	
	 void traitement (String X)   //X est le nom de Operations
	 {
		 try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements definition
				doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("definition");
				doc.appendChild(rootElement);
				
				// abstr element
				Element abstr = doc.createElement("abstr");
				rootElement.appendChild(abstr);
				// set attribute to abstr element
				Attr attr = doc.createAttribute("id");
				attr.setValue("abstract");
				abstr.setAttributeNode(attr);
				
				//List string a supprimer-------------------------------------delete
				List<String> KeywordList= new ArrayList<String>(); KeywordList.add("test 1");KeywordList.add("test 2");KeywordList.add("test 3");KeywordList.add("test 4");
				
				List<Element> PartList= new ArrayList<Element>(); PartList.add(PartFrom2Strings("nom1","Type1")); PartList.add(PartFrom2Strings("nom2","Type2")); PartList.add(PartFrom2Strings("nom3","Type3"));
				//------------------------------------------------------------delete
		
				// keywords element
				Element keywords = Keywords(KeywordList);
				abstr.appendChild(keywords);
				
				// message element
				Element message = doc.createElement("message");
				abstr.appendChild(message);
							
				// message element
				Element part = doc.createElement("part");
				message.appendChild(part);
							
				//Operations
				Element operations = doc.createElement("operations");
				abstr.appendChild(operations);
				// set attribute to porttype element
				Attr attrportType = doc.createAttribute("name");
				attrportType.setValue(X);
				operations.setAttributeNode(attrportType);
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("xmls/file.xml"));

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);
				System.out.println("File saved!");

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
			
		 
	 }
	 
	 
	 
	//---------------------------------Les fonctions------------------------------------------------------------
	 
	 
	 Element Keywords (List<String> L){    // retourne l'element keywords depuis une liste de String
			Element keywordsElement = doc.createElement("keywords");

			for (int i = 0; i < L.size(); i++) {
				Element keyword = doc.createElement("keyword");
				keyword.appendChild(doc.createTextNode(L.get(i)));
				keywordsElement.appendChild(keyword);

			}
			return keywordsElement;
			}
	 
	 
	 Element PartFrom2Strings (String name, String type){
			Element part = doc.createElement("part");
			part.setAttribute("name", name);
			part.setAttribute("type", type);			
			return part;
			}
	 
	 
	 Element SetMessageFromPartList(List<Element> partlist,String MessageName ) 
	 {		
			Element message = doc.createElement("message");	 
			for (int i = 0; i < partlist.size(); i++) {
			Element tempel = partlist.get(i);
			message.appendChild(tempel);
			message.setAttribute("name", MessageName);
			 }
		return message;
	 }
	 
	 
	 Element SetinOutFromMessageName(String InOut, String MessageName  ) 
	 {		
			Element InpOut = doc.createElement(InOut);	 
					
			InpOut.setAttribute("message", MessageName);
			 
			return InpOut;
		 }

	 
	 Element ConditionfromStrings (String PrePostcondition, String name,String content){ // retourne un element pré/postCondition depuis son nom , contenue, et type ( pre/post)
			Element condition = doc.createElement(PrePostcondition);
			condition.setAttribute("name", name);
			condition.setTextContent(content);	
	return condition;
			}
	 
	 
	 Element OperationFromString ( String name,Element input, Element output, List<Element> prepost){ // retourne un element operation
		 Element operation = doc.createElement("operation");
			operation.setAttribute("name", name);
			operation.appendChild(input);
			operation.appendChild(output);
			 Element conditions = doc.createElement("conditions");
			 operation.appendChild(conditions);
				for (int i = 0; i < prepost.size(); i++) {
					operation.appendChild(	prepost.get(i));	
				}
			return operation;
			}
	 
	 
	public static void main(String args[]) {

		 new XMLCreator().traitement("Arithmitique");
	
	
	}

	
	
	
}