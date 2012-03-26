
@GrabResolver(name='repo.grails.org', root='http://repo.grails.org/grails/repo/', m2Compatible='true')
@Grapes(
  [
    @Grab(group='org.grails', module='grails-spring', version='2.0.1')
  ]
)

import grails.spring.BeanBuilder
import groovy.util.logging.*

class MyClass{
  def p1
  def p2
  String toString(){
      return "p1: ${p1}, p2: ${p2}"
    }
};

// create app context with myClass scoped as prototype (by default)
def builder = new BeanBuilder().beans{
  
  myClass(MyClass){ bean ->
    bean.scope = "prototype"
    p1 = 0
    p2 = 0
    
  }
}

println "myClass - prototype scope"

def appContext = builder.createApplicationContext()
def myClass1 = appContext.myClass
def myClass2 = appContext.myClass

myClass1.p1 = 5
myClass1.p2 = 10

println "myClass1 -> ${myClass1}"
println "myClass2 -> ${myClass2}"

myClass2.p1 = 6
myClass2.p2 = 11

println "myClass1 -> ${myClass1}"
println "myClass2 -> ${myClass2}"

println "myClass - singleton scope"

// create app context with myClass class scoped as singleton
builder = new BeanBuilder().beans{
  
  myClass(MyClass){ bean ->
    bean.scope = "singleton"
    p1 = 0
    p2 = 0
  }
}

appContext = builder.createApplicationContext()
myClass1 = appContext.myClass
myClass2 = appContext.myClass

myClass1.p1 = 5
myClass1.p2 = 10

println "myClass1 -> ${myClass1}"
println "myClass2 -> ${myClass2}"

myClass2.p1 = 6
myClass2.p2 = 11

println "myClass1 -> ${myClass1}"
println "myClass2 -> ${myClass2}"

