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

2. There must be at least two layers of case: 1) the primary case of a tail word which is determined by a dependency label and 2) the secondary case of a word, which agrees with the primary case of another word. This separation allows the parser to count on the primary case for determining attachment and labeling without wrongly losing the restriction that words must agree with each other in case.

In turn, the text analysis must be grounded in experiential semantics in the following two ways:

1. If a word represents something material or abstract, whether already observed or not, it is a noun. In this sense, *intelligere* (*intelligence*) and *essentia* (*essence*) are both nouns here no matter whether the same strings could represent other types of elements in other wordings. This means that we must count on the overall frequency and the context of wording to the right and to the left to determine the best word class for a given word before parsing.

2. If a word represents something material or abstract, it is a noun no matter whether it agrees with other nouns in case, number, and gender. In this sense, *suum* and *sua* (*his*) are as much a noun as *eius* (*his*) and *dei* (*Gods*) despite their secondary case, number, and gender.

Once words such as *suum* and *sua* (*his*) and words like *intelligere* (*intelligence*) are classified as nouns, the wordings *suum intelligere*, *intelligere dei*, *intelligere eius*, and *sua essentia* will correspond to the syntagma **[(noun) (noun)]**. The word **(genitive noun)** will depend on the word **(common noun)** no matter the order. This will result two specific syntagmata: **[(genitive noun) (common noun)]** for a forward dependency and **[(common noun) (genitive noun)]** for a backward dependency. This dependency shall be called *Possessor* and it shall include most "nmod" and some "amod" from the previous data. The actual translation rules are located in the `ittb-ip.dux` file.

### Step 2

Let us continue paying attention to adjectives that depend on verbs as "amod" according to ITTB annotation. Here we find another kind of linguistic phenomenon that deserves our attention as for how words have been classified. More than half of the adjectives with this anchoring (attachment + labeling) have the lemma *primus* (37 out of 70) and more than a sixth have the lemma *immobilis* (12 out of 70). Let us look at the two types of grammatical structure where *primus* (*first*) and *immobilis* (*the non-moving*) take this role, starting with those cases where the author answers to the question "*who did it first?*".

FORM        | sed        | primum     | movens     | seipsum    | movetur    | semper
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |cconj       |adj         |verb        |pron        |verb        |adv

FORM        | deus       | cum        | sit        | primum     | movens     | immobile
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |propn       |sconj       |aux         |adj         |verb        |adj

FORM        | cum        | ipse       | sit        | primum     | movens     
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |sconj       |pron        |aux         |adj         |verb        

FORM        | deus       | qui        | est        | primum     | movens
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |propn       |pron        |aux         |adj         |verb

The examples read *but the one who first moves moves forever*, *if God is the one who first moves the non-moving*, *when he is the one who first moves*, *as God is the one who first moves*. In all these examples, the word *primum* (*first*) sorts ('ordinates') all processes of moving and picks out ('superfers') the one that happened the earliest in time. In that sense, we are talking about an 'ordinal' number 1, which opposes *ultimus* (*last*) in temporal direction: the earliest vs the latest, the second earliest vs the second latest, etc.. It also opposes *secundum* (*second*), *tertium* (*third*), *quartum* (*fourth*)... In this sense, *primum* is an ordinal number, a kind of number that opposes cardinal numbers such as *unum* (*one*). In this case, it is used here to sort processes in a timeline. In addition, both *seipsum* (*himself*) and *immobile* (*the non-moving*) are the things being moved. The transitive verb *movens* in *movens seipsum* is synonymous to the intransitive verb *movetur* (*moving on one's own*). The intransitive verb *movens* (*moving*) is more general and includes both non-affecting verb *movens seipsum*/*movetur* (*moving on one's own*) and the affecting verb *movens immobile* (*moving the non-moving*). These categories of verbs and complements have no correspondence in the syntagma below them (sequence of word classes).

Taking these considerations into account, I shall propose new classes for these words.

FORM        | sed        | primum     | movens     | seipsum    | movetur    | semper
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |            |number      |verb        |noun        |verb        |adverb
NOUN-CLASS  |            |            |            |pronon      |            |
CASE        |            |            |            |accusative  |            |
CASE-SEAM   |            |accusative2 |         |            |            |
KIND        |            |ordinal     |            |            |            |
FOLIAGE     |            |            |ō-foliage   |            |or-foliage  |

FORM        | deus       | cum        | sit        | primum     | movens     | immobile
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |noun        |            |verb        |number      |verb        |noun
NOUN-CLASS  |proper noun |            |            |            |            |common noun
CASE        |nominative  |            |            |accusative  |            |accusative
CASE-SEAM   |            |            |            |accusative2 |         |
KIND        |            |            |            |ordinal     |            |
FOLIAGE     |            |            |ō-foliage   |            |ō-foliage   |

FORM        | cum        | ipse       | sit        | primum     | movens     
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |            |noun        |verb        |number      |verb        
NOUN-CLASS  |            |pronoun     |            |            |            
CASE        |            |nominative  |            |            |            
CASE-SEAM   |            |            |            |accusative2 |
KIND        |            |            |            |ordinal     |            
FOLIAGE     |            |            |ō-foliage   |            |ō-foliage

FORM        | qui        | est        | primum     | movens
:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |noun        |verb        |number      |verb
NOUN-CLASS  |pronoun     |            |            |
CASE        |nominative  |            |            |
CASE-SEAM   |            |            |accusative2 |
KIND        |            |            |ordinal     |
FOLIAGE     |            |ō-foliage   |            |ō-foliage

With these syntagma, *seipsum* (*himself*) and *immobile* (*the non-moving*) are nouns and they can serve as Object or Goal for the action of moving. Since **(accusative noun)** is the standard class for Objects/Goals, the backward dependency in **[(ō-foliage verb) (accusative noun)]** can be extrapolated from the two first examples together with many other examples in the corpus. In turn, verb transitivity can be inferred from the presence or absence of an object. This means that **[(verb) (Goal:seipsum)]** and **[(or-foliage verb)]** can be interpreted as non-affecting, **[(verb) (Goal:-seipsum)]** as affecting, and **[(ō-foliage verb)]** as both.

Moreover, the verb *movens* is embedded in a nominal group and it represents *the one who moves*. For this reason, it can be the a argument for verbs such as *est*, *sit* and *movetur* as a nominal group (see discussion on ranks). And, finally, an **(accusative ordinal number)** is always dependent on a verb in the syntagma **[(accusative ordinal number) (verb)]**. When searching for the attachments with nouns for *primus*, we find wordings such as *primus motus* (*the first movement*) and *primus motor* (*the first mover*). In all those cases, we can see *primus* functioning as an Ordinator. For lemmata such as *movere* (*move*), *motus* (*movement*) and *motor* (*mover*), the process is ordered in time. For nouns representing material things such as *motor* (*mover*), *auctor* (*actor*), and *corpus* (*body*), there is a linguistic potential for an order in space (*the first/second drawer from left to right*) or in scale (*the [second] largest mountain*). This is however absent in the corpus. In Thomas's work, all nouns represent things as participants in actions, therefore *primum corpus* (*the first body*) is the body that was first created, not the first body in a spatial order. The attachment to nouns can be learned from the syntagma **[(ordinal number) (common noun)]**. All of these dependencies are easy to learn with the proposed word classification. The transformations for step 2 can also be found in the file `ittb-ip.dux`.

NOTE: A variant of *primum* is *ad primum*.

Another two functions of ordinal numbers can be seen in this corpus. Numbering arguments and referring back to numbered arguments.

#### Numbering arguments

- _**tertium inconveniens** est quod..._
- _**tertia objectio** est quia..._
- _**tertium** quod..._

The examples read *the third drawback is that...*, *my third objection is that...*, *third that...*. These themes (headings) place the coming arguments in a textual list for future reference. Here the argument starting with *quod* or *quia* receives an attribute, which is either the ordinal number alone, or the ordinal number plus a category.

### Referring back to numbered arguments

- _**tertia probatio** in idem redit, nisi..._
- _ex **tertio vero** sequitur quod..._
- _ex quo **tertia ratio** solvitur_

The examples read *the third proof takes us to the same conclusion, even if...*, *from the third fact, it follows that...*, *from which the third problem is solved*. In these wordings, the author refers back to an argument that was numbered previously.

In all these cases, the ordinal number functions as an Ordinator within a nominal group. In turn, the nominal group serves as an Attribute for a clause in a clause complex when the author is numbering arguments and it serves as an Argument for a verb in a clause when the author is referring back to numbered arguments. These functions are covered by the transformations proposed above as they stand.

### Step 3

TODO

In this step, we completed the translation of all "Amod" relations.

### Step 4

Let us now move our attention to the other three major functions an adjective has in the ITTB corpus: namely, head of "Cop", head of "Nsubj", and tail of "Xcomp". From the multiple structures we see in the corpus including these functions, I shall pick out one linguistic phenomenon where these functions occur: namely, attributive clauses.

FROM        |quod        |deus        |est         |bonus 
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |propn       |aux         |adj
STRUCTURE   |            |Nsubj       |Cop         |Head

FROM        |quod        |est         |bonum 
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |aux         |adj
STRUCTURE   |Nsubj       |Cop         |Head

FROM        |ratione     |cuius       |sol         |calidus     |dicitur
:----------:|:----------:|:----------:|:----------:|:----------:|:----------: 
WORD-CLASS  |noun        |pron        |noun        |adj         |verb
STRUCTURE   |            |            |Nsubj-Pass  |Xcomp       |Head

The examples read *that God is good*, *which is good*, and *for which reason the Sun is deemed hot*. Here we see two clauses that are very similar in meaning, but which differ in syntagma and structure. The syntagmata are **[(propn) (aux) (adj)]**, **[(pron) (aux) (adj)]** and **[(noun) (adj) (verb)]** and they correspond to the structures **[(Nsubj) (Cop) (Head)]** and [(Nsubj-Pass) (Xcomp) (Head)]. In Step #1, we fixed the classification of nouns by making proper nouns, common nouns, and pronouns be classes of nouns. This solves the issue of learning a structure such as **[(Nsubj) (Cop) (Head)]** for different noun classes as we have seen above. However, the semantic similarities between clauses with the verb "sum" and those with the verb "dicitur" is not reflected by a grammatical similarity in structure and syntagma. Having this in mind, I propose the following change in word classes and functions:

  
FROM        |quod        |deus        |est         |bonus 
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |verb        |adjective
NOUN-CLASS  |            |proper noun |            |
STRUCTURE   |            |Carrier     |Head        |Attribute

FROM        |quod        |est         |bonum 
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |verb        |adjective
NOUN-CLASS  |pronoun     |            |
STRUCTURE   |Carrier     |Head        |Attribute

FROM        |ratione     |cuius       |sol         |calidus     |dicitur
:----------:|:----------:|:----------:|:----------:|:----------:|:----------: 
WORD-CLASS  |            |            |noun        |adjective   |verb
NOUN-CLASS  |            |            |common noun |            |
STRUCTURE   |            |            |Carrier     |Attribute   |Head

With these changes, the word classes **(noun) (adjective) (verb)**, **(noun) (noun) (verb)**, **(noun) (adposition) (verb)**, and **(noun) (adverb) (verb)** will correspond to the structure **(Carrier) (Attribute) (Head)** for a select list of verbs including *est*, *dicitur*, *videtur*, *invenitur*, *fit*, *proponitur*, *ponitur*, *nominatur*, *conspicitur*. For the parser to be successful in recognizing these structures, it will need to balance the structuring power between the syntagmata, the verb lemmata, and inflectional features of the verb such as the opposition between "ō-foliage" versus "or-foliage". Some learning algorithms are better at this than others, and this should be taken into consideration when choosing one. 

Moving on, there is another structure for attributive clauses in the data, which includes the Attributor, the person who does the attribution. Here is one can see the syntagmata and the structures we find in the corpus as it stands. 

FROM        |sed         |hoc         |est         |impossibile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |cconj       |pron        |aux         |adj
CASE        |            |nom         |            |nom
STRUCTURE   |            |Nsubj       |Cop         |Head


FROM        |et          |hoc         |videtur     |probabile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |cconj       |pron        |verb        |adj
CASE        |            |nom         |            |nom
STRUCTURE   |            |Nsubj-Pass  |Head        |XComp


FROM        |hoc         |autem       |habet       |aristoteles |pro         |impossibili
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |cconj       |verb        |propn       |adp         |adj
CASE        |acc         |            |            |nom         |            |abl
STRUCTURE   |Obj         |            |Head        |Nsubj       |            |XComp

These examples read *but that is impossible*, *and that seems probable*, *Aristotle, on the other hand, holds that for impossible*. Again, these examples are similar in meaning, but that similarity is not reflected in the syntagmata and structures proposed for the wording. In turn, this makes machine learning difficult. The first two examples are already simpler in the proposed analysis. We only need a new analysis for the third one. Here is one that is likely to provide a good result in both parsing and visualization.

FROM        |sed         |hoc         |est         |impossibile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |verb        |adjective
NOUN-CLASS  |            |pronoun     |            |
CASE        |            |nominative  |            |
CASE-SEAM   |            |            |            |nominative-seam
STRUCTURE   |            |Nsubj       |Cop         |Head

FROM        |et          |hoc         |videtur     |probabile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |verb        |adjective
NOUN-CLASS  |            |pronoun     |            |
CASE        |            |nominative  |            |
CASE-SEAM   |            |            |            |nominative-seam
STRUCTURE   |            |Nsubj-Pass  |Head        |XComp


FROM        |hoc         |autem       |habet       |aristoteles |pro         |impossibili
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |            |verb        |noun        |adposition  |adjective
NOUN-CLASS  |pronoun     |            |            |proper noun |            |
CASE        |accusative  |            |            |nominative  |            |
CASE-SEAM   |            |            |            |            |            |ablative-seam
STRUCTURE   |Carrier     |            |Head        |Attributor  |Mark        |Attribute

The transitive structure follows the same pattern as the intransitive ones. Here the **(accusative noun)** is the Carrier, the **(nominative noun)** is the Attributor, and the **(adjective)** is the Attribute. This transitive structure is supported by a handful of verbs, which are analogous with the ones in the intransitive. The opposition may occur in the lemma as in *est* and *habet* or *fit* and *facit*, or else it takes place in the foliage as in *dicitur* and *dicit*, *nominatur* and *nominat*, or *vocatur* and *vocat*. This means that a parser can transfer some of the lemma collocations learned for intransitive structure to the parsing of transitive ones. It can also transfer the associations between word classes and functions to from the intransitive to the transitive. This transfer is especially relevant because the number of transitive structures for attributive clauses is quite small. Finally, I opted to raise the adposition to the clause level because it is part of the lexical word _**habet** quid **pro** quo_ (_**holds** something **for** something_). This shall improve the parsing down the line because it will reduce the number of syntagma and labeling options available for the phrase _**pro** quo_ (_**for** something_).





 