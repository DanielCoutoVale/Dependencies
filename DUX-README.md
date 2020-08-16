# DUX FORMAT

DUX is a format for querying a Dependency Base. It is compatible with both Universal Dependencies (UD) and Intelligenti Pauca (IP).

A Dependency Base keeps named corpora and their texts. Each text is represented as an enumeration of wordings and each wording is an enumeration of words. Every word in a Dependency Base has a serial id such as 13492342 and a structured id such as (ITTB, Gentiles, 1, 2). The structured id represents the second word of the first wording of the text "Gentiles" from the corpus "ITTB". The DUX format includes queries, updates, and documents.

# DUX Query

A DUX query is meant to retrieve a tuple of one or more words related to each other through `functions`, which can be either `dependencies` or `ties`. In its simplest format, a DUX query is composed of a single pattern for a word. There are three different ways to describe a word:

```
[13492342]
[ITTB, Gentiles, 1, 2]
[aux #sum 'sit']
```

One can specify a word by its serial id, by its structured id within parentheses, or by its features within square brackets. The word form is specified as a token between single quotes, the word lemma is specified as a token starting with a hash mark, and other features such as word class are specified by tokens without marks.

Queries can be carried out in two contexts: either when retrieving words by their features in a given analysis in a given description or when converting an analysis from a source description to a target one. In the former case, no prefix is added to the features because they belong to the only linguistic description in the context. In the later case, one has to add either S: or T: as prefixes, specifying which description the feature belongs to.   

```
[aux #sum 'sit']
[S:aux #sum 'sit']
[T:verb #sum 'sit']
```

Further information can be given about the features. One can specify the system name to which the feature belongs. This is a good practice for large descriptions and for descriptions where multiple systems have features with the same name, especially in scripts one would like to keep.

```
[WORD-CLASS:aux #sum 'sit']
[S:WORD-CLASS:aux #sum 'sit']
[T:WORD-CLASS:verb #sum 'sit']
```

When one wants to retrieve word tuples, one needs to specify the function that words must have relative to one another. Functions are represented by a token followed by a tuple of two numbers, referring to the positions of the words in the query. The first line below retrieves a tuple of two words: the first is a verb, the second is a noun, and the second functions as the Actor of the first. In the second line below, one retrieves a tuple of three words: the first is a verb, the second and the third are nouns, the second functions as the Carrier of the first and the third as the Attribute of the first. The third line retrieves the same word sets as the second, only their order is different.

```
[verb] [noun] Actor(2,1)
[verb] [noun] [noun] Carrier(2,1) Attribute(3,1)
[noun] Carrier(1,3) [verb] [noun] Attribute(4,3)
```

One can specify more information concerning functions. Each function belongs to a metafunction, that is, a function in the situation of interaction. The function of Actor belongs to the EXPERIENTIAL metafunction. Moreover, the rank at which words relate to one another can also be specified as shown below. This further specification can be done both when there is a single description in the context, or when there is a source and a target description.

```
[WORD-CLASS:verb] [WORD-CLASS:noun] EXPERIENTIAL:Actor(2:groups,1:clause)
[T:WORD-CLASS:verb] [T:WORD-CLASS:noun] T:EXPERIENTIAL:Actor(2:groups,1:clause)
[S:WORD-CLASS:verb] [S:WORD-CLASS:noun] S:MIXED:Nsubj(2:word,1:word)
```

# DUX Command

A DUX command is composed of two parts. A DUX query and a DUX update to be carried out. Here are some examples of DUX commands:

```
[S:verb]  => +T:verb +T:lexical-verb
[S:aux]   => +T:verb +T:auxiliary-verb
[S:propn] => +T:noun +T:proper-noun
[S:noun]  => +T:noun +T:common-noun
[S:pron]  => +T:noun +T:pronoun
[S:adj]   => +T:adjective
```

These commands select all words of specified UD classes and they add an IP class and an IP subclass to these words. In these examples, the '+' symbol indicates that the specified feature should be added to the words matching the query. Word features may be added, then removed. Here are examples where this is done when converting UD to IP.

```
[T:adjective #primus] => -T:adjective +T:number +T:ordinal
[T:adjective #secundus] => -T:adjective +T:number +T:ordinal
[T:adjective #tertius] => -T:adjective +T:number +T:ordinal
[T:adjective #meus] => -T:adjective +T:noun +T:pronoun
[T:adjective #tuus] => -T:adjective +T:noun +T:pronoun
[T:adjective #suus] => -T:adjective +T:noun +T:pronoun
```

The symbol '-' indicates that the specified feature should be removed from words matching the query. Sometimes, however, we want to add features to or remove them from a word depending on the way this word is linked to others. This is shown below:

```
[T:verb] [T:adjective] S:Amod(2,1)  => -T:verb -T:lexical-verb +T:noun +T:common-noun
[T:adjective] [T:adjective] S:Amod(2,1)  => -T:adjective +T:noun +T:common-noun
```

All verbs and adjectives modified by adjectives in UD are nouns in IP. When making a conversion, the first word of the tuple is the word to which features are added or removed.

At last, but not least, there are the links. When converting an analysis from a source description to a target one, one needs to add and remove links between words in the target analysis. Here are some examples of how this is done.

```
[T:noun] [T:adjective] S:Amod(2,1)  => +T:Classifier(2:groups,1:group)
[T:noun] [#bonus] T:Classifier(2,1) => -T:Classifier(2,1) +T:Epithet(2:groups,1:group)
```

When adding a link, one must specify the ranks. When removing links, specifying the ranks is optional.

# DUX Document

A DUX document is composed of one or multiple files. One of the files is selected for execution. There are five kinds of line in a DUX file:

```
1.
4. # this is a comment
2. STOP
3. IMPORT <pathname>
5. [S:adj]  => +T:adjective
```

1. An empty line is not an operation and it has no effect.
2. A command line is not an operation and it has no effect.
3. A stop command stops the execution so that one can verify the conversion until that point.
4. An import command moves execution to a file whose path is `<pathname>.dux`.
5. A DUX command behaves as explained in the previous section.

