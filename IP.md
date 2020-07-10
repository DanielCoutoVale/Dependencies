# Intelligentī Pauca
A systemic network for ranked universal dependencies

Latin corpora such as ITTB, AGLDT, and PROIEL have been annotated with word classes and dependencies from the cross-linguistic description called **Universal Dependencies** (UD). These classes and dependencies are meant to be universal across all languages. However, despite the enormous impact in comparability of study results, text analyses with this linguistic description lack a grounding on ideational, interpersonal, and textual semantics. In turn, from a pedagogical perspective, the low groundedness of this linguistic description can have a negative impact in the utility of parsing results for teaching Latin; and from a computational perspective, text analyses in this linguistic description are hard to learn automatically. In particular, this low learnability adds a toll to the reliability of parsers for languages such as Latin for which linguistic data is scarce. In this section, I shall illustrate the issues I am referring to -- namely, low groundedness and low learnability -- and I shall demonstrate how to solve them by translating current text analyses from Universal Dependencies into a new Latin description that I am calling **Intelligentī Pauca** (IP).

## Learnability

Statistical parsers often rely on the sequence of word classes and cases to determine 1) which word depends on which (attachment) and 2) which role the tail word plays relative to the head one (labeling). This means that a linguistic description that results in less attachment options and less labeling options for the same sequence of word classes will be learned with less data than a linguistic description that results in more options. Taking this into consideration, let us take a look at the number of attachments + labelings that exist for a tail adjective in ITTB dev corpus.

Frequency   |Tail class  |Dependency  |Head class 
:----------:|:----------:|:----------:|:----------:
1041|adj|Amod|noun
97|adj|Xcomp|verb
80|adj|Obl|verb
70|adj|Amod|verb
64|adj|Conj|adj
51|adj|Amod|pron
48|adj|Nsubj|verb
44|adj|Nmod|noun
42|adj|Csubj|verb
39|adj|Advcl|verb
36|adj|Nsubj:Pass|verb
32|adj|Obj|verb
26|adj|Acl:Relcl|pron
20|adj|Conj|verb
17|adj|Obl:Arg|verb
16|adj|Ccomp|verb
15|adj|Conj|noun
13|adj|Amod|adj
13|adj|Csubj:Pass|verb
11|adj|Acl:Relcl|noun
11|adj|Nsubj|adj
10|adj|Advcl|adj
9|adj|Nsubj|noun
7|adj|Amod|num
7|adj|Nmod|adj
7|adj|Csubj|adj
6|adj|Amod|propn
6|adj|Obl|adj
5|adj|Conj|pron
5|adj|Nmod|pron
4|adj|Amod:Advmod|noun
4|adj|Acl|pron
4|adj|Advcl|noun
4|adj|Acl:Relcl|verb
3|adj|Acl|noun
3|adj|Amod:Advmod|pron
3|adj|Acl:Relcl|adj
3|adj|Advcl|adv
3|adj|Csubj|pron
3|adj|Nmod|num
3|adj|Advcl|pron
2|adj|Iobj|verb
2|adj|Ccomp|noun
2|adj|Conj|num
2|adj|Nsubj|pron
2|adj|Obl|noun
2|adj|Nmod|verb
2|adj|Conj|adv
1|adj|Orphan|verb
1|adj|Appos|noun
1|adj|Conj|propn
1|adj|Nmod:Appos|adj
1|adj|Amod:Advmod|propn
1|adj|Acl|adj
1|adj|Advmod:Emph|noun
1|adj|Advmod:Emph|verb
1|adj|Obl|pron
1|adj|Obl|adv
1|adj|Acl:Relcl|propn
1|adj|Csubj|noun
1|adj|Acl|verb

As we can see in the table above, adjectives play various roles relative to words of various classes in ITTB. In total, the current annotation of ITTB has 62 attachment-labeling options for adjectives, half of which occur less than 5 times. Such an annotation is much more difficult to learn than one that has a single option. In the following, I shall demonstrate that reducing the number of attachment and labeling options is not only doable, but also that the parsing results shall become more useful for multiple tasks.

## Adjectives

### Step 1

Let us focus on the most frequent dependency "amod" for an adjective and let us look up what is going on in the second most frequent attachment of that dependency: namely, the attachment to a "verb". I shall get three examples that properly illustrate one of the linguistic phenomena that has been tagged in this way.

FORM        |quod        |intelligere |dei         |est         |sua         |essentia
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |verb        |propn       |aux         |adj         |noun
CASE|       |nom         |gen         |            |            |nom         |nom

FORM        |quod        |suum        |intelligere |sit         |sua         |essentia
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adj         |verb        |aux         |adj         |noun
CASE        |            |nom         |            |            |nom         |nom

FORM        |quod        |intelligere |eius        |sit         |simplex
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |verb        |pron        |aux         |adj
CASE        |            |            |gen         |            |nom

The three examples read respectively *that God's intelligence is his essence*, *that his intelligence is his essence*, and *that his intelligence is simple*. In the analysis, we see that *intelligere* (*intelligence*) is a verb and that *essentia* (*essence*) is a noun. The words *suum* and *sua* (*his*) are adjectives and they depend on either a verb such as *intelligere* (*intelligence*) or a noun such as *essentia* (*essence*). The label for this dependency is "amod". 

Still in these examples, the word *eius* (*his*) has a similar meaning to *suum* and *sua*, but it is not an adjective. It is a pronoun. In turn, the word *dei* (*God's*) is a proper noun. There is no overlap between the sequences of word classes despite the fact that *God's intelligence*, *his intelligence*, and *his essence* are one and the same thing, that *God* and *he* are one and the same person, and that the relation between *God* and *his intelligence*/*essence* is the same in all examples.

When it comes to cases, some of the words modifying *intelligere* and *essentia* are nominative (*suum* and *sua*) and the others are genitive (*dei* and *eius*). As a consequence, despite the fact that these wordings are extremely similar grammatically and semantically, these similarities are not reflected by the classes of words and their cases.

Based on this observation, I shall propose another way to classify these words, aiming at improving the learnability for parsing.

FORM        |quod        |intelligere |dei         |est         |sua         |essentia
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |noun        |verb        |noun        |noun
NOUN-CLASS  |            |common noun |proper noun |            |pronoun     |common noun
CASE        |            |nominative  |genitive    |            |genitive    |nominative
CASE-SEAM   |            |            |            |            |nominative2 |

FORM        |quod        |suum        |intelligere |sit         |sua         |essentia
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |noun        |verb        |noun        |noun
NOUN-CLASS  |            |pronoun     |common noun |            |pronoun     |common noun
CASE        |            |genitive    |nominative  |            |genitive    |nominative
CASE-SEAM   |            |nominative2 |            |            |nominative2 |

FORM        |quod        |intelligere |eius        |sit         |simplex
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |noun        |verb        |adjective
NOUN-CLASS  |            |common noun |pronoun     |            |
CASE        |            |nominative  |genitive    |            |
CASE-SEAM   |            |            |            |            |nominative2 |

In the new analysis, a parser can learn that a genitive noun always modifies a common noun in these examples and that, if the genitive noun has a secondary case, this case agrees with the primary case of the common noun. These dependency rules are much easier to learn than the previous ones. There are fewer rules: namely, two.

To achieve this higher learnability, the linguistic description must have two features:

1. There must be at least two classification layers for words: one for word classes, and another for noun classes. This makes a parser learn rules applicable to all nouns with less examples without wrongly broadening the rules that should apply to a single class of nouns.

2. There must be at least two layers of case: 1) the primary case of a tail word which is determined by a dependency label and 2) the secondary case of any word, which agrees with the primary case of another word. This separation allows the parser to count on the primary case for determining attachment and labeling without wrongly loosing the restriction that words must agree with each other in case.

In turn, the text analysis must be grounded in experiential semantics in the following two ways:

1. If a word represents something material or abstract, whether already observed or not, it is a noun. In this sense, *intelligere* (*intelligence*) and *essentia* (*essence*) are both nouns here no matter whether the same strings could represent other types of elements in other wordings. This means that we must count on the overall frequency and the context of wording to the right and to the left to determine the best word class for a given word before parsing.

2. If a word represents something material or abstract, it is a noun no matter whether it agrees with other nouns in case, number, and gender. In this sense, *suum* and *sua* (*his*) are as much a noun as *eius* (*his*) and *dei* (*Gods*) despite their secondary case, number, and gender.

Once words such as *suum* and *sua* (*his*) and words like *intelligere* (*intelligence*) are classified as nouns, the wordings *suum intelligere*, *intelligere dei*, *intelligere eius*, and *sua essentia* will correspond to the syntagma **[(noun) (noun)]**. The word **(genitive noun)** will depend on the word **(common noun)** no matter the order. This will result two specific syntagmata: **[(genitive noun) (common noun)]** for a forward dependency and **[(common noun) (genitive noun)]** for a backward dependency. This dependency shall be called *Possessor* and it shall include most "nmod" and some "amod" from the previous data. The actual translation rules are located in the `ittb-ip.dux` file.

### Step 2

TODO

*deus igitur cum sit primum movens immobile, est primum desideratum.*

TODO
