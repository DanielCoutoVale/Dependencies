# DUX FORMAT

Here we shall explain the DUX format, a format for querying a Dependency Base, now compatible with both Universal Dependencies (UD) and Intelligenti Pauca.

A Dependency Base keeps named corpora with titled texts. Each text is stored as an enumeration of wordings and each wording is an enumeration of words. Every word in a Dependency Base has a serial id such as 13492342 and a structured id such as (ITTB, Gentiles, 1, 2), meaning the second word of the first wording of the text "Gentiles" from the corpus "ITTB". The DUX format is composed of two subformats. A DUX query and a DUX command.

# DUX Query

A DUX query is meant to retrieve a group of one or more words related to each other through links (dependencies or ties). In its simplest format, a DUX query is composed of a single word. Here are three ways of specifying a word:

```
13492342
(ITTB, Gentiles, 1, 2)
[aux #sum 'sit']
```

One can specify a word by its serial id, by its structured id within parentheses, or by its features within square brackets. The word form is specified as a token between single quotes, the word lemma is specified as a token starting with a hash mark, and other features such as word class is specified by tokens without marks.

# DUX Command



# DUX Line