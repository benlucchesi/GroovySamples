
println "I'm a script!"
println ("... oh yeah... Hello, World!");

println "\nHere's my ${args.length} arguements: " + args

println "Here's my arguments (for loop indexing into collection):"
for( def i = 0; i<args.length; i++ ){
  println "arg ${i}: ${args[i]}"
}

println "\nHere's my arguments (for-in loop over range upto length of collection):"
for( i in 0..args.length-1 ){
  println "arg ${i}: ${args[i]}"
}

def i=0
println "\nHere's my arguments (for-in loop over elements of collection):"
for( arg in args ){
  println "arg ${i++}: ${arg}" 
}

println "\nHere's my arguments (each closure over elements of collection):"
args.each({ arg ->
  println "arg ${i++}: ${arg}"
})

println "\nHere's my arguments (each with index closure):"
args.eachWithIndex{ arg, j ->
  println "arg ${j++}: ${arg}"
}


def MyFunction(param1,param2,param3)
{
    println "Inspect the parameters: "
    println "Param1 (${param1.getClass()}): ${param1}"
    println "Param2 (${param2.class}): ${param2}"
    println "Param3 (${param3.class.name}): ${param3}"
    
    println "returning ${param1} + ${param2} + ${param3}" 
    param1 + param2 + param3  // no need to use "return", but you can
}

println "\nCall a function (integer parameters):"
println MyFunction(1,2,3)

println "\nCall a function pointer (string parameters):"
def c = this.&MyFunction
println c("a","b","c")

// declare a new closure with three parameters
c = { param1, param2, param3 ->
    println "Inspect the parameters: "
    println "Param1 (${param1.getClass()}): ${param1}"
    println "Param2 (${param2.class}): ${param2}"
    println "Param3 (${param3.class.name}): ${param3}"
    
    println "returning ${param1} + ${param2} + ${param3}" 
    param1 + param2 + param3  // no need to use "return", but you can
}
println "\nCall a closure (mixed parameters):"
println c(1,"two",3.0)
