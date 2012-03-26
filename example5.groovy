
@GrabResolver(name='repo.grails.org', root='http://repo.grails.org/grails/repo/', m2Compatible='true')
@Grapes([
    @Grab(group='org.grails', module='grails-spring', version='2.0.1'),
    @Grab(group='org.aspectj', module='aspectjweaver', version='1.6.9'),
    @Grab(group='cglib', module='cglib', version='2.2.2')
  ]
)

import grails.spring.BeanBuilder
import org.springframework.aop.*
import groovy.util.logging.*

class MyClass{
  def p1
  def p2
  def myMethod(param1, param2){
    return( param1 + param2 )
  }
};

class MyAspect {
    void afterMethodCalled(org.aspectj.lang.JoinPoint jp, MyClass mc){
      println "after method called with args: "
      jp.args.each{
        println it
      }
    }

   void afterPropertySet(org.aspectj.lang.JoinPoint jp){
    println "after property set... ${jp.args[0]} -> ${jp.args[1]}"
   }

  void beforePropertyGet(org.aspectj.lang.JoinPoint jp){
    println "before property get..."
   }
};

def builder = new BeanBuilder().beans{
  
  xmlns aop:"http://www.springframework.org/schema/aop"

  myClass(MyClass){ // scope is prototype by default
    p1 = 0
    p2 = 0
  }

  myAspect(MyAspect)

  aop {
    config("proxy-target-class":true){
      aspect( id: "myClassMethodCalled", ref: "myAspect"){
        after method: "afterMethodCalled",
        pointcut: "execution(* MyClass.*(..)) && this(myClass) && !(execution(* MyClass.set*(..)) || execution(* MyClass.get*()))"
      }
      aspect( id: "myClassPropertySet", ref: "myAspect"){
        after method: "afterPropertySet",
        pointcut: "execution(* MyClass.set*(..))"
      }
      aspect( id: "myClassPropertySet", ref: "myAspect"){
        before method: "beforePropertyGet",
        pointcut: "execution(* MyClass.get*(..))"
      }
    }
  }
}

def appContext = builder.createApplicationContext()
def var = appContext.myClass

println var.p1
println "myMethod(1,2) returned: " + var.myMethod(1,2)
var.p1 = "new value"
