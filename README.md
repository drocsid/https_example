An example parsing JSON using Circe and decoding via HTTP4S. Based on the gitter8 HTTP4s template.

# created via
sbt new http4s/http4s.g8

Given the Giter8 template https://github.com/http4s/http4s.g8 it wasn't completely obvious to me how to follow the Jokes example as a pattern to create my own decoder given my API. I reached out for assistance in the gitter8 template and was lucky to obtain some assitance. Then I'm documenting my pitfalls to hopefully assist other developers.

Circe ADTs and Encoder / Decoders

I started the project by first trying to define a Circe Decoder and decoding JSON locally. From the first commit SHA https://github.com/drocsid/https_example/commit/adf17b789caac199022c15328df4852bb2d4bf62 you can see Weather.scala which contains my ADT and GenericDerivation object based on https://circe.github.io/circe/codecs/adt.html . One mistake on my part might be that I placed the GenericDerivation decoder / encoder instances in this object within the same Weather.scala . Another
issue 
