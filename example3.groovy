

import groovy.xml.StreamingMarkupBuilder
import groovy.util.XmlSlurper
import groovy.xml.XmlUtil
import org.custommonkey.xmlunit.*
@Grab(group='xmlunit', module='xmlunit', version='1.3')

// create some XML from static content
def xml1 = new StreamingMarkupBuilder().bind{
  BookList {
    Book( [ISBN: "54321"] ){
      Title("War and Peace")
      Author("Leo Tolstoy")
    }
    Book( ISBN: "12345" ) {
      Title( "Fear and Loathing in Las Vegas") 
      Author( "Hunter S. Thompson" )
    }
  }
}

println xml1

// create some XML from content stored in a collection
def booklist = [
  [ISBN: "54321", Title: "War and Peace", Author: "Leo Tolstoy"], 
  [ISBN: "12345", Title: "Fear and Loathing in Las Vegas", Author: "Hunter S. Thompson" ]
]

def xml2 = new StreamingMarkupBuilder().bind{
  BookList {
    booklist.each{ book ->
      Book( ISBN: book.ISBN ){
        Title( book.Title )
        Author( book.Author )
      }
    }
  }
}

println xml2

XMLUnit.ignoreWhitespace = true
def comparison = new Diff(xml1.toString(),xml2.toString())
assert comparison.identical()

// deserialize some XML
def books = new XmlSlurper().parseText(xml1.toString()) // returns a GPathResult

// examine the contents returned.
println "Discovering XML content"
books.children().each{ book ->
  println "Book: "
  println "   attributes:"
  book.attributes().each{ name, value ->
    println "      ${name}: ${value}"
  }
  println "   elements:"
  book.children().each{ child ->
    println "      ${child.name()}: ${child.text()}"
  }
}

println "\n\nDirectly accessing paths in XML"
books.Book.each{ book ->
  println "Book: "
  println "   attributes:"
  println "     IDSN: ${book.@ISBN}"
  println "   elements:"
  println "     Title: ${book.Title}"
  println "     Author: ${book.Author}"
}

// update the XML
println "\n\nUpdate the XML in GPathResult"
books.Book[0].@ISBN = "98765"

// printout some pretty XML
println XmlUtil.serialize(books)
