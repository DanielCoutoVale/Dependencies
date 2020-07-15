# Intelligentī Pauca
A systemic network for ranked universal dependencies

This section explains the translation of anchors for adjectives.

For an introduction to IP, see [the motivation for IP](IP.md).

## Adjectives

Adjectives have 62 anchors in ITTB. In this section, our goal is to reduce the number of anchors to less than 10 and to achieve a better distribution of frequencies between different anchors. The goal is to achieve a compromise between distribution and meaningfulness. The analysis translation shall be achieved in 5 steps, covering 5 linguistic phenomena:

1. "adj" words that should be considered nouns
2. "adj" words that should be considered numbers
3. how to split "Amod" into 5 functions in a meaningful way 
4. how to describe attributive clauses systemically
5. how to cover group complexes for adjectives

### Step 1

Let us focus on the most frequent function "amod" for an adjective and let us look up what is going on in the second most frequent attachment of that function: namely, the attachment to a "verb". I shall get three examples that properly illustrate one of the linguistic phenomena that has been tagged in this way.

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

Let us continue paying attention to adjectives that depend on verbs as "amod" according to ITTB annotation. Here we find another kind of linguistic phenomenon that deserves our attention as for how words have been classified. More than half of the adjectives with this anchor have the lemma *primus* (37 out of 70) and more than a sixth have the lemma *immobilis* (12 out of 70). Let us look at the two types of grammatical structure where *primus* (*first*) and *immobilis* (*the non-moving*) take this role, starting with those cases where the author answers to the question "*who did it first?*".

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

The examples read *but the one who first moves moves forever*, *if God is the one who first moves the non-moving*, *when he is the one who first moves*, *as God is the one who first moves*. In all these examples, the word *primum* (*first*) sorts all processes of moving and picks out the one that happened the earliest in time. In that sense, we are talking about an 'ordinal' number 1, which opposes *ultimus* (*last*) in temporal direction: the earliest vs the latest, the second earliest vs the second latest, etc.. It also opposes *secundum* (*second*), *tertium* (*third*), *quartum* (*fourth*)... In this sense, *primum* is an ordinal number, a kind of number that opposes cardinal numbers such as *unum* (*one*). In this case, it is used here to sort processes in a timeline. In addition, both *seipsum* (*himself*) and *immobile* (*the non-moving*) are the things being moved. The transitive verb *movens* in *movens seipsum* is synonymous to the intransitive verb *movetur* (*moving on one's own*). The intransitive verb *movens* (*moving*) is more general and includes both non-affecting verb *movens seipsum*/*movetur* (*moving on one's own*) and the affecting verb *movens immobile* (*moving the non-moving*). These categories of verbs and complements have no correspondence in the syntagma below them, thus they are hard to learn automatically.

Taking these considerations into account, I shall propose new classes for these words.

FORM        | sed        | primum     | movens     | seipsum    | movetur    | semper
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |            |number      |verb        |noun        |verb        |adverb
NOUN-CLASS  |            |            |            |pronon      |            |
CASE        |            |            |            |accusative  |            |
CASE-SEAM   |            |accusative2 |            |            |            |
KIND        |            |ordinal     |            |            |            |
FOLIAGE     |            |            |ō-foliage   |            |or-foliage  |

FORM        | deus       | cum        | sit        | primum     | movens     | immobile
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
CLASS       |noun        |            |verb        |number      |verb        |noun
NOUN-CLASS  |proper noun |            |            |            |            |common noun
CASE        |nominative  |            |            |accusative  |            |accusative
CASE-SEAM   |            |            |            |accusative2 |            |
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

With these syntagmata, *seipsum* (*himself*) and *immobile* (*the non-moving*) are nouns and they can serve as Object or Goal for the action of moving. Since **(accusative noun)** is the standard class for Objects/Goals, the backward dependency in **[(ō-foliage verb) (accusative noun)]** can be extrapolated from the two first examples together with many other examples in the corpus. In turn, verb transitivity can be inferred from the presence or absence of an object. This means that **[(verb) (Goal:seipsum)]** and **[(or-foliage verb)]** can be interpreted as non-affecting, **[(verb) (Goal:-seipsum)]** as affecting, and **[(ō-foliage verb)]** as both.

Moreover, the verb *movens* is embedded in a nominal group and it represents *the one who moves*. For this reason, it can be the a argument for verbs such as *est*, *sit* and *movetur* as a nominal group (see discussion on ranks). And, finally, an **(accusative ordinal number)** is always dependent on a verb in the syntagma **[(accusative ordinal number) (verb)]**. When searching for the attachments with nouns for *primus*, we find wordings such as *primus motus* (*the first movement*) and *primus motor* (*the first mover*). In all those cases, we can see *primus* functioning as an Ordinator. For lemmata such as *movere* (*move*), *motus* (*movement*) and *motor* (*mover*), the process is ordered in time. For nouns representing material things such as *motor* (*mover*), *auctor* (*actor*), and *corpus* (*body*), there is a linguistic potential for an order in space (*the first/second drawer from left to right*) or in scale (*the [second] largest mountain*). This is however absent in the corpus. In Thomas's work, all nouns represent things as participants in actions, therefore *primum corpus* (*the first body*) is the body that was first created, not the first body in a spatial order. The attachment to nouns can be learned from the syntagma **[(ordinal number) (common noun)]**. All of these dependencies are easy to learn with the proposed word classification. The transformations for step 2 can also be found in the file `ittb-ip.dux`.

NOTE: A variant of *primum* is *ad primum*.

### Step 3

In Steps 1 and 2, we reclassified "adj" words that are better mapped to structures as nouns and numbers. Now we can move on to the remainder of adjectives that depend on nouns. These adjectives can have multiple functions within a nominal group as we shall see below. Let us start by a special group of adjectives used for quantifying things, which oppose cardinal numbers.

FORM        |tria        |inconvenientia
:----------:|:----------:|:----------:
WORD-CLASS  |num         |noun
STRUCTURE   |Nummod      |Head

FORM        |plura       |animalia
:----------:|:----------:|:----------:
WORD-CLASS  |adj         |noun
STRUCTURE   |Amod        |Head

FORM        |multis      |fabulis
:----------:|:----------:|:----------:
WORD-CLASS  |adj         |noun
STRUCTURE   |Amod        |Head

The examples read *three drawbacks*, *two or more animals*, and *multiple stories*. Both cardinal numbers and words such as *plura* and *multis* quantify the things being represented. So both "Nummod" and "Amod" should be turned "Quantifiers" in this case.

FORM        |tria        |inconvenientia
:----------:|:----------:|:----------:
WORD-CLASS  |number      |noun
NUMBER-KIND |cardinal    |
STRUCTURE   |Quantifier  |Head

FORM        |plura       |animalia
:----------:|:----------:|:----------:
WORD-CLASS  |adjective   |noun
NUMBER-KIND |            |
STRUCTURE   |Quantifier  |Head

FORM        |multis      |fabulis
:----------:|:----------:|:----------:
WORD-CLASS  |adjective   |noun
NUMBER-KIND |            |
STRUCTURE   |Quantifier  |Head

With these changes, words serving as Quantifiers in nominal groups shall be easily found teachers, students, and researchers. By specifying by specifying both the function and the class ('number' or 'adjective'), they will be able to visualize only the desired subgroup. In addition, these changes preserve the learnability of the original annotation.

There is a second way whereby things are described numerically, in fact what is counted is not the number of things, but the number of parts or values that something has. These numbers serve as answers to the question *How many parts does it have?* or *How many does it count as?* Let us take a look at some examples:

FORM        |duplex      |officium
:----------:|:----------:|:----------:
WORD-CLASS  |adj         |noun
STRUCTURE   |Amod        |Head

FORM        |diversas    |res         
:----------:|:----------:|:----------:
WORD-CLASS  |adj         |noun
STRUCTURE   |Amod        |Head

FORM        |uni         |simplici    |rei
:----------:|:----------:|:----------:|:----------: 
WORD-CLASS  |num         |adj         |noun
STRUCTURE   |Nummod      |Amod        |Head

FORM        |per         |formas      |diversas
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adp         |noun        |adj         
STRUCTURE   |            |Head        |Amod

FORM        |secundum    |formas      |multiplices
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adp         |noun        |adj         
STRUCTURE   |            |Head        |Amod

The examples read *double work*, *different things*, *one simple thing*, *by different forms*, and *by multiple forms*. Whereas *simple* and *double* in *one simple thing* and *double work* function as Multipliers, both the words *diversas* and *multiplices* function as Quantifiers. The words 'simplex', 'duplex', ... can be taken as multiplicative numbers in this context. Here is a reorganization of the syntagmata and structures for improving parsing:

FORM        |duplex      |officium
:----------:|:----------:|:----------:
WORD-CLASS  |number      |noun
NUMBER-KIND |multiplicative|
STRUCTURE   |Multiplier  |Head

FORM        |diversas    |res         
:----------:|:----------:|:----------:
WORD-CLASS  |adjective   |noun
NUMBER-KIND |            |
STRUCTURE   |Quantifier  |Head

FORM        |uni         |simplici    |rei
:----------:|:----------:|:----------:|:----------: 
WORD-CLASS  |number      |number      |noun
NUMBER-KIND |cardinal    |multiplicative|
STRUCTURE   |Quantifier  |Multiplier  |Head

FORM        |per         |formas      |diversas
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adposition  |noun        |adjective   
NUMBER-KIND |            |            |      
STRUCTURE   |            |Head        |Quantifier

FORM        |secundum    |formas      |multiplices
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adposition  |noun        |adjective   
NUMBER-KIND |            |            |      
STRUCTURE   |            |Head        |Quantifier

The remaining adjectives do not need class shifting and their functions can be distributed by their lemmata. Here is a table with one possible division:

FUNCTION    |WORD-CLASS  | LEMMATA
:----------:|:----------:|:----------:
Quantifier  |adjective   |diversus, multiplex, multus, simplex, singulus, solus, varius
Quantifier  |number      |unus/singulus, duo, tres, quatuor
Multiplier  |number      |simplex, duplex
Deictic     |adjective   |analogicus, certus, communis, consimilis, contrarius, dissimilis, evidens, inferus, invariabilis, localis, manifestus, omnimodus, particularis, posterus, praesens, principalis, proximus, similis, solus, specialis, superior, unicus, universus, univocus
Epithet     |adjective   |absolutus, activus, aequalis, aequivocus, affirmativus, altus, angelicus, antiquus, astutus, bestialis, bonus, cognoscitivus, completus, conformis, contemplativus, continuus, debilis, demonstrativus, effectivus, efficax, efficiens, fabulosus, falsus, familiaris, finalis, ignarus, immobilis, impossibilis, incommunicabilis, indoctus, infinitus, innumerabilis, intellectivus, intellectualis, intelligibilis, interminabilis, longus, magnus, mirabilis, modestus, necessarius, negativus, nimius, nobilis, novus, operativus, parvus, passivus, paucus, perfectus, perpetuus, plenus, potior, probabilis, purus, rudis, sacer, sanctus, scibilis, sempiternus, sensibilis, sensitivus, sensualis, singularis, studiosus, sublimis, subsistens, subtilis, summus, superessentialis, verisimilis, verus, vetus, violentus, visibilis
Classifer   |adjective   |accidentalis, aeternus, albus, aliqualis, ambo, apostolicus, appetibilis, bellicus, caelestis, carnalis, catholicus, christianus, circularis, corporalis, corporeus, corruptibilis, dimensivus, essentialis, exterior, extraneus, extrinsecus, formalis, gentilis, gubernatorius, humanus, immortalis, imperfectus, inanimatus, inconveniens, incorporeus, infinitus, materialis, mathematicus, medicinalis, medius, mobilis, modicus, mundanus, naturalis, pigmentarius, prior, proprius, rationalis, regius, sacrilegus, sapiens, senarius, spiritualis, substantialis, supernaturalis, temporalis, universalis, verbalis

Because of the rough mapping between lemmata and classes of adjectives, a parser can easily determine the function of an adjective within a nominal group. In the table below you can see the distribution of functions that used to be "Amod". 

A-Frequency |R-Frequency |Role
:----------:|:----------:|:----------:
300         |34.40%      |Epithet
213         |24.43%      |Classifier
161         |18.46%      |Ordinator
124         |14.22%      |Quantifier
60          |6.88%       |Deictic
14          |1.61%       |Multiplier

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

The examples read *that God is good*, *which is good*, and *for which reason the Sun is deemed hot*. Here we see three clauses that are very similar in meaning, but which differ in syntagma and structure. The syntagmata are **[(propn) (aux) (adj)]**, **[(pron) (aux) (adj)]** and **[(noun) (adj) (verb)]** and they correspond to the structures **[(Nsubj) (Cop) (Head)]** and **[(Nsubj-Pass) (Xcomp) (Head)]**. In Step #1, we fixed the classification of nouns by making proper nouns, common nouns, and pronouns be classes of nouns. This solves the issue of learning a structure such as **[(Nsubj) (Cop) (Head)]** for different noun classes as we have seen above. However, the semantic similarities between clauses with the verb "sum" and those with the verb "dicitur" is not reflected by a grammatical similarity in structure and syntagma. Having this in mind, I propose the following change in word classes and functions:

  
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

These examples read *but that is impossible*, *and that seems probable*, *Aristotle, on the other hand, holds that for impossible*. Again, these examples are similar in meaning, but that similarity is not reflected in the syntagmata and structures proposed for the wording. In turn, this makes machine learning difficult. The first two examples are already simpler in the proposed analysis. We only need a new analysis for the third example. Here is one that is likely to provide a good result in both parsing and visualization.

FROM        |sed         |hoc         |est         |impossibile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |verb        |adjective
NOUN-CLASS  |            |pronoun     |            |
CASE        |            |nominative  |            |
CASE-SEAM   |            |            |            |nominative-seam
STRUCTURE   |            |Carrier     |Head        |Attribute

FROM        |et          |hoc         |videtur     |probabile
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |noun        |verb        |adjective
NOUN-CLASS  |            |pronoun     |            |
CASE        |            |nominative  |            |
CASE-SEAM   |            |            |            |nominative-seam
STRUCTURE   |            |Carrier     |Head        |Attribute


FROM        |hoc         |autem       |habet       |aristoteles |pro         |impossibili
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |            |verb        |noun        |adposition  |adjective
NOUN-CLASS  |pronoun     |            |            |proper noun |            |
CASE        |accusative  |            |            |nominative  |            |
CASE-SEAM   |            |            |            |            |            |ablative-seam
STRUCTURE   |Carrier     |            |Head        |Attributor  |Mark        |Attribute

The transitive structure of the third example follows the same pattern as the intransitive ones. Here the **(accusative noun)** is the Carrier, the **(nominative noun)** is the Attributor, and the **(adjective)** is the Attribute. This transitive structure is supported by a handful of verbs, which are analogous with the ones in the intransitive. The opposition may occur in the lemma as in *est* and *habet* or *fit* and *facit*, or else it takes place in the foliage as in *dicitur* and *dicit*, *nominatur* and *nominat*, or *vocatur* and *vocat*. This means that a parser can transfer some of the lemma collocations learned for intransitive structure to the parsing of transitive ones. It can also transfer the associations between word classes and functions from the intransitive to the transitive. This transfer is especially relevant because the number of transitive structures for attributive clauses is quite small. Finally, I opted to raise the adposition to the clause level because it is part of the lexical word _**habet** quid **pro** quo_ (_**holds** something **for** something_). This shall improve the parsing down the line because it will reduce the number of syntagma and labeling options available for the phrase _**pro** quo_ (_**for** something_), and also bring the number of phrases **[(adposition) (adjective)]** closer to zero.

### Step 5

There is a final linguistic phenomenon that we have to treat for adjectives before we move on to verbs: namely, the difference between a group and a group complex. Let us see the example below for an example of group complex.

FORM        |quae        |homines     |maxime      |perfectos   |et          |bonos       |facit
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |noun        |adv         |adj         |cconj       |adj         |verb
GROUP |Head |Head        |advmod >    |Head        |cc >        |Head        |Head
GROUP-COMPLEX|           |            |            |Head        |            |conj <      |
CLAUSE      |Nsubj       |Obj         |            |Xcomp       |            |            |Head

The example reads *which makes men utmost perfect and good*. Here the group complex is *maxime perfectos et bonos* (*utmost perfect and good*) is a single grammatical unit that is composed of two groups: namely, *maxime perfectos* (*utmost perfect*) and *et bonos* (*and good*). The composed grammatical unit is a group complex and it counts as a single unit in the rank above. The function of the secondary group in a group complex is that of Extender.

There are 261 other dependencies for adjectives, but they will be better dealt with in the section for nouns. This completes the reduction of adjective anchoring options. The result can be seen in the tables below:

#### Number of adjectives and anchoring options 

Description |Word class  |Frequency   |Options     |Options > 1%
:----------:|:----------:|:----------:|:----------:|:----------:
UD          |adj         |2135        |61          |13
IP          |adjective   |1511        |18          |8

#### Frequency of anchoring options in UD
Frequency   |Tail class  |Head class  |Dependency   
:----------:|:----------:|:----------:|:----------:
48.76%      |adj         |noun        |Amod
04.54%      |adj         |verb        |Xcomp
03.75%      |adj         |verb        |Obl
03.28%      |adj         |verb        |Amod
03.00%      |adj         |adj         |Conj

#### Frequency of anchoring options in IP
Frequency   |Tail class  |Head class  |Dependency
:----------:|:----------:|:----------:|:----------:
36.47%      |adjective   |verb        |Attribute
19.79%      |adjective   |noun        |Epithet
13.63%      |adjective   |noun        |Classifier
04.17%      |adjective   |noun        |Quantifier
03.77%      |adjective   |noun        |Deictic
03.38%      |adjective   |adjective   |Extender

### Final remark

The number of adjectives in the corpus was reduced. The number of anchors was reduced from 61 to 18, which means the regularity between word classes and functions was increased substantially, as well as the chances of a correct parse. The application of a parser to new vocabulary was definitely increased for numbers, since numbers not in this corpus that receive correct classification by a POS tagger will render correct parses even if they were no present in the training corpus. In turn, the transitivity of verbs was interpreted for attributive clauses, and parsing results that take this corpus as training set will present results that are easier to use for teaching Latin.
