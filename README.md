# SLR Parser generator with synthesized attributes for Scala

## Usage

Create a `Grammar` object, then create `Generator` object, providing name and package for grammar and run
its' `generateIn` method to generate ADT for your grammar's AST and a method to parse string into this ADT.

All classes and traits, as well as method for parsing, will be generated in package object in package you named. If
directories do not exist, they will be created.

## Advantages

`Grammar` object can be created very convenient using Scala syntax and for-expression. There are lots of syntax
extension providing convenient methods for creating terminal, non-terminals, grammar rules and binding non-terminals to
regexes.

If you use for-expression and syntax extensions for `Grammar` creation, all synthesized attributes and conversion
functions will by type-checked in compilation time, leaving you with no unexpected `ClassCastException`'s in runtime.

## Disadvantages

Generated ADT names are taken from terminals/nonTerminals names and may be inconvenient to use, especially rules' names.

If you use synthesized attributes and provide conversion functions using lambdas, then .class file with your grammar
must be available in the classpath to run parser.

## Examples

There are several examples of grammar description in the repository:

1. [Simple arithmetic calculator](./src/main/scala/eliseevh/grammar/calculator/ExpressionCalculatorGrammar.scala) -
   Basic arithmetic expressions parser with evaluation on synthesized attributes.
2. [GoodLang](./src/main/scala/eliseevh/grammar/goodlang/GoodLangToCGrammar.scala) - Small programming language with
   functions, variables and simple input/output, that is translated to C using attributes. (Only integer and boolean
   values are supported).
3. [NonSlr](./src/main/scala/eliseevh/grammar/nonslr/NonSlrGrammar.scala) - Grammar that is not SLR. For simplicity, it
   does not use any attributes. If you try to generate parser it will fail with exception.

## Implementation details

To typecheck arbitrary-sized conversion rule, some kind of heterogeneous list is created (see `RuleRhsTyped` trait).
Probably, it is stored not in optimal way, which can lead into `StackOverflowError` when compiling non-trivial grammars,
requiring to increase sbt's stack size.

When constructing lexer and parser, all attribute and conversion function types are erased (function converted so it
accepts `List[Any]`). During parsing, all attributes are treated as Any.

Also, information about rules, terminals and non-terminal is preserved on parsing stage only using string field
containing name. Result of parsing is just a tree. All information about types, rules and terminals/non-terminals is
restored after parsing using function that uses casts based on these string field's value. Such function is generated
and put in same package object with ADT. It is called `fromTree`, you can see how it looks like
for [calculator example](./src/main/scala/eliseevh/calculator/generated/package.scala) (line 37)

No information about grammar(except ADT) is directly put in generated code. It is stored inside `Parser` and `Lexer`
objects, which are serialized with compression into string which is put into generated code. Then, they are decompressed
are deserialized in runtime, restoring control tables.
