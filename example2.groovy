

// declare a table
def mytable = [:]
println  "${mytable}, ${mytable.getClass()}"

// assign a value to a table and read it out
mytable['a'] = "entry 1"
println "${mytable}, ${mytable['a']}"
println "${mytable}, ${mytable.a}"

// assign a value to a table and read it back out
mytable.b = 'entry 2'
println "${mytable}, ${mytable['b']}"
println "${mytable}, ${mytable.b}"
println "${mytable}, ${mytable.c}"
println "${mytable.getClass()}"
println "${mytable.class}"

// get the keys and values
mytable.keySet().each{println it}
mytable.values().each{println it}
mytable.each{ k,v -> println "${k} : ${v}" }
mytable.each{ v -> println "${v}" }

// assign a closures to a table
mytable["addTwoThings"] = { a, b -> a + b }
mytable.subtractTwoThings = { a, b -> a - b }

println mytable.addTwoThings(1,2)
println mytable["subtractTwoThings"](1,2)

interface IWat{
  def Wat();
}

mytable.Wat = { "quack" }

def wat = mytable as IWat
println wat.Wat()

mytable.Wat = { "WAT!" }
println wat.Wat()

mytable.animalSays = "woof!"
mytable.Wat = { animalSays }
mytable.Wat.delegate = mytable
println mytable.Wat()

