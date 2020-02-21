# An example parsing JSON using Circe and decoding via HTTP4S. 

Based on the gitter8 HTTP4s template.

In this example we decode the API response from the weather.gov points endpoint such as https://api.weather.gov/points/33.3287,-84.375


## How I arrived at a solution based on the gitter8 module.

Given the Giter8 template https://github.com/http4s/http4s.g8 it wasn't completely obvious to me how to follow the Jokes example as a pattern to create my own decoder given my API. I reached out for assistance in the gitter8 template and was lucky to obtain some assitance. Then I'm documenting my pitfalls to hopefully assist other developers.


## Circe ADTs and Encoder / Decoders

I started the project by first trying to define a Circe Decoder and decoding JSON locally. From the first commit SHA https://github.com/drocsid/https_example/commit/adf17b789caac199022c15328df4852bb2d4bf62 you can see `Weather.scala` which contains my `ADT` and `GenericDerivation` object based on the points `API` and using https://circe.github.io/circe/codecs/adt.html as a reference. 

The reason I started with an `ADT` as a first step was that I assumed that if I could decode a `JSON` string with the decoder that I'd be able to use that same decoder via `HTTP4S`.

I created a test case to prove I could decode a valid JSON string.

## Regarding Weather.scala at this point probably my biggest misses were:

1) I created a `Weather` object containing my `ADT` trait / case classes. The trait and case classes can / should be top level. At this time I was calling that trait Shape2 (I named it this because reading regarding shapeless made me reason about creating typeclasses...) 

2) I created the `GenericDerivation` object within this same file containing the encoder / decoders. More on this later...

Next I took the `Weather.scala` and used it to create a `Weathers.scala` to integrate the decoder with `HTTP4S` logic similar to the `Jokes.scala` example.


## Regarding Weathers.scala misses at this point including inherited above from Weather.scala:

1) the `GenericDerivation` object could not allow the implicit decoders to be put in scope because the decoders needed to be part of an associated object for the trait used by the typeclass. 


The first step was towards the solution after discussion was sorting out mess above. I removed the `Weathers.scala` class and removed the outer object from `Weather`.scala. I removed the `GenericDerivation` object into a serperate file. I renamed the trait from `Shape2` to `Weather`. I created an associated object for the trait containing the http4s methods such as `impl` and `get` based on `Jokes.scala` as a reference.


## Regarding Weather.scala refactor misses at this point include

1) The decoder / encoder that were moved to the seperate `GenericDerivation` object need to be part of the `Weather` object

2) The signature for the `impl` method was based on the `Jokes.scala` example and using a return type `Weather[F]` but in our solution we changed the return type to `F[Weather]` which had implications. I'm not yet versed in `cats` so I didn't have the tools to intuit the necessecary changes. (more on this below)



## After these changes a number of commits were made against `Weather.scala` in order to get a solution https://github.com/drocsid/https_example/commits/master

We removed the get method from the trait and this appears to be related to (2) above. Then changes in our impl signature seemed to necessitate removal of the constructor for the `Weather` object and get methods which were defined in `impl` method and called by the Route method defined for Weather. The Route definition needed to change due to the removal of said `get` method again furthermore due to the return type (2) above. Finally we needed to add the encoders. I'm not sure why we need them because I decode the JSON string without them, but they are necessary maybe due to HTTP4S... 

^^I was pretty lost regarding this reasoning and was lucky to have guidance.


## Including the above dicussion misses somewhere between these commits include

1) Changes to the return type for `impl` from `Weather[F]` to `F[Weather]` . ( If I make time to learn `cats` I might be able to understand this )

2) Removal of the `get` method which might be necessitated by (1)

3) The signature of the weather route needed to change compared to the `Jokes.scala` implementation by operating on a `(C: Client[F])`. This is related to (1) above.

4) Needed to add the encoders to the `Weather` object in order to make the client work.



## TLDR If my babble is incomprehensible I will try to summarize the hardest parts from my limited perspective

1) `ADT` examples show the decoder as part of `GenericDeriviation` object but we needed to make the encoder / decoder part of the traits attached object. Then the pattern differs from the ADT example in the Circe docs and maybe doesn't consider the application from `HTTP4S`

2) Without comprehending `cats` changing the return types from `Weather[F]` to `F[Weather]` and related changes. I could not bridge this gap myself.

3) Somewhat significant differences required between the `Jokes` example in the gitter8 module and what we did to make a working example for my `ADT`.


## Open questions I have myself

1) Should I be using Optics to interact with the API response vs ADT? Are there examples of this for HTTPS? What are the PROs / CONs of that approach aside from the rigor required defining the ADT?

## Feedback

If you have any feedback regarding my approach, want to discuss, criticize, or address my open questions feel free.






