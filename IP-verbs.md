# Intelligentī Pauca
A systemic network for ranked universal dependencies

This section explains the translation of anchors for verbs.

For an introduction to IP, see [the motivation for IP](IP.md).

## Verbs
There are 60 anchors for verbs ('aux' or 'verb') in the ITTB corpus of which 2/3 (42) happen for less than 1% of the verbs. Our goal is to reduce the number of anchors with frequency higher than 1% to under 10. To accommodate the relations between verbs, we shall cover five linguistic phenomena:

1. Auxiliaries versus process verbs 
2. Finite versus non-finite verbs
3. Verb modes
4. Free and bound clauses
5. Clause contacts

### Step 1

Let us start by focusing on verbal groups. As mentioned in the motivation of the IP description, one of our aims is to provide teachers with grammatical structures that are useful for teaching the options in meaning that authors encounter in Latin. These grammatical structures must be represented graphically on a computer screen and these graphic representations should highlight those similarities in wording that correspond to similarities in meaning. For instance, one aspect of verbal groups that students must master is that, usually, one verb represents a process in which people and other things take part while the other verbs are responsible for determining the subject, determining polarity (true, false), modulating the statement (possibly true, probably true, certainly true), and setting the time of this process relative to the time of interaction (happened, is happening, will happen). We say that the verb representing the process is the lexical verb and that the other verbs are its auxiliaries. Here is an example of the graphical representation we are aiming at.

non           |potest      |esse
:------------:|:----------:|:----------:
Adjunct       | Auxiliary  |Head

possibile est |non         |moveri
:------------:|:----------:|:----------:
Auxiliary     |Adjunct     |Head

impossibile est |esse
:--------------:|:----------:
Auxiliary       |Head

In the UD description, we do not have the necessary dependencies for a verbal group. Let us look into some examples of the mapping between syntagma and structure in the ITTB corpus, contrasting clauses where we find auxiliary verbs with those where there are none.

FORM        |quod        |in          |deo         |non         |est         |accidens
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adp         |propn       |adv         |verb        |noun
STRUCTURE-1 |            |Case >      |Head        |            |            |
STRUCTURE-2 |Mark        |            |Obl         |Advmod      |Head        |Nsub

FORM        |quod        |in          |deo         |non         |potest      |esse        |malum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adp         |propn       |adv         |verb        |verb        |noun
STRUCTURE-1 |            |Case >      |Head        |            |Head        |< Xcomp     |
STRUCTURE-2 |Mark        |            |Obl         |Advmod      |Head        |            |Nsub

FORM        |quod        |in          |deo         |nihil       |est         |violentum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adp         |propn       |pron        |aux         |adj
STRUCTURE-1 |            |Case >      |Head        |            |            |
STRUCTURE-2 |Mark        |            |Obl         |Nsub        |Cop         |Head

FORM        |quod        |in          |deo         |nihil       |potest      |esse        |violentum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adp         |propn       |pron        |verb        |aux         |adj
STRUCTURE-1 |            |Case >      |Head        |            |            |Cop >       |Head
STRUCTURE-2 |Mark        |            |Obl         |Nsub        |Head        |            |Xcomp

The examples above read *that in God there is no flaw*, *that in God there can be no evil*, *that in God nothing is violent*, and *that in God nothing can be violent*. In UD terms, the finite verb is always the head of the clause. In existential clauses, the noun is the subject of the verb *est* only when it is finite. If a finite verb such as *potest* is inserted, it becomes the subject of that verb instead. The infinitive verb is an 'Xcomp' of the finite verb. As for attributive clauses, the adjective is the head of the clause if there are no finite verbs. The noun is its subject and the auxiliary is its copula. When a finite verb such as *potest* is inserted, the noun becomes its subject and the adjective becomes its 'Xcomp'. As we can see, there is very little stability between word classes and the anchors associated with them.

Taking this observation into consideration, I shall propose another structure and other word classes so as to forge a stability between class and function, thus increasing the number of rules that can be transferred between examples.

FORM        |quod        |in          |deo         |non         |est         |accidens
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |            |            |adverb      |verb        |noun
VERB-CLASS  |            |            |            |            |lexical     |
GROUP       |            |            |            |Adjunct     |Head        |
CLAUSE      |            |            |            |            |Head        |Existent

FORM        |quod        |in          |deo         |non         |potest      |esse        |malum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |            |            |adverb      |verb        |verb        |noun
VERB-CLASS  |            |            |            |            |auxiliary   |lexical     |
GROUP       |            |            |            |Adjunct     |Auxiliary   |Head        |
CLAUSE      |            |            |            |            |            |Head        |Existent

FORM        |quod        |in          |deo         |nihil       |est         |violentum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |            |            |noun        |verb        |adjective
VERB-CLASS  |            |            |            |            |lexical     |
GROUP       |            |            |            |            |Head        |
CLAUSE      |            |            |            |Carrier     |Head        |Attribute

FORM        |quod        |in          |deo         |nihil       |potest      |esse        |violentum
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |            |            |            |noun        |verb        |verb        |adjective
VERB-CLASS  |            |            |            |            |auxiliary   |lexical     |
GROUP       |            |            |            |            |Auxiliary   |Head        |
CLAUSE      |            |            |            |Carrier     |            |Head        |Attribute

In the new proposal, *non* (*no*), an **(adverb)**, always serves as an Adjunct for the **(lexical verb)**. In addition, *potest* (*can*), an **(auxiliary verb)**, always serves as an Auxiliary for the **(lexical verb)**. This creates a stable mapping between word classes and structure at the rank of groups. In turn, the verb *esse* may represent an existential process when it is followed by a **(noun)**, and it may represent an attributive process when it is preceded by a **(noun)** and followed by an **(adjective)**. Therefore, the mappings between the syntagma and the structure can be transferred between clauses with and without *potest*. The actual translation rules are provided in the `ittb-ip.dux` file.

In particular, there are auxiliary verbs such as *necesse est* and *possibile est*, which function like an attributive clause embedded in the verbal group. Here are the syntagmata and the structures according to UD and IP descriptions.

FORM        |omne        |corpus      |...         |possibile   |est         |non         |moveri
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |noun        |            |adj         |aux         |adv         |verb
STRUCTURE-1 |            |Nsubj       |            |            |            |Advmod      |Head
STRUCTURE-2 |            |            |            |Head        |Cop         |            |Csubj

FORM        |omne        |corpus      |...         |possibile   |est         |non         |moveri
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adjective   |noun        |            |adjective   |verb        |adverb      |verb
VERB-CLASS  |            |            |            |            |auxiliary   |            |lexical
CLAUSE-1    |            |            |            |Attribute   |Head        |            |
GROUP       |Deictic >   |Head        |            |            |Auxiliary > |Adjunct >   |Head
CLAUSE-2    |            |Goal        |            |            |            |            |Head

An IP analysis results in a more stable syntagma-structure mapping. Here we see an attributive clause embedded in a verbal group, which means we rank a clause *possibile est* down to the word rank, so that we can take it as an alternative option to the auxiliary verb *potest*. For this to be achieved, a small class of adjectives needs to be either manually created or automatically learned based on the lemmata. This class should include modal adjectives such as *possibile* (*possible*), *impossibile* (*impossible*), and *necesse* (*necessary*) as well as truth adjectives such as *verum* (*true*) and *falsum* (*false*).

There are four grammatical structures in this dependency tree and we can be represent them graphically in the following way: 

#### Groups

omne        |corpus
:----------:|:----------:
Deicitc     |Head

possibile est |non         |moveri
:------------:|:----------:|:----------:
Auxiliary     |Adjunct     |Head

#### Clauses

possibile   |est
:----------:|:----------:
Attribute   |Head

omne corpus             |possibile est non moveri
:----------------------:|:----------------------:
Goal                    |Head

The commands for translating an analysis from UD to IP as shown above can be found in the `ittb-ip.dux` file.

### Step 2 

Let us now move towards the other end of the verbal group and let us identify the last verb in that chain. Except for the last example, I shall be using parsing results, not original texts for these examples. The original sentence for these variants is *quod ... nec perfectum posse dici videtur* (*that does not seem to be possibly said perfect*).

FORM        |quod        |perfectum   |dicitur
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |adj         |verb
STRUCTURE   |Nsubj-Pass  |Xcomp       |Head

FORM        |quod        |perfectum   |potest      |dici
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |pron        |adj         |verb        |verb
STRUCTURE-1 |            |Xcomp       |            |Head
STRUCTURE-2 |Nsubj       |            |Head        |Xcomp

FORM        |quod        |perfectum   |posse       |dici        |videtur
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
STRUCTURE-1 |            |Xcomp       |Head        |            |    
STRUCTURE-2 |            |            |Xcomp       |Head        |
STRUCTURE-3 |Nsubj-Pass  |            |            |Xcomp       |Head

The examples read *that is said perfect*, *that can be said perfect*, and *that seems to be possibly said perfect*. There are multiple issues with this analysis as for teaching and automatic learning. The first is the fact that for each new verb the grammatical structure gets one level deeper. The second is the fact that the pronoun *quod* (*that*) switches between being a subject and a passive subject with the finite verb, loosing its dependency with the previous verb. The third is the fact that this pronoun serves as subject for a different lemma in each example. Finally, the attachments of the adjective *perfectus* and the verb *dico* are also unstable. The adjective is either attached to the verb *potest* or to the verb *dico* and the verb *dico* is either the head, an Xcomp of the verb *potest* or an Xcomp of the verb videtur. This amounts to a parsing result that is difficult to learn automatically, is potentially incorrect according to annotator's intent, and is hard to use for teaching Latin.

Based on this, I shall propose a different way of dealing with finite and non-finite verbs within a verbal group, aiming at achieving a stronger mapping between word classes and word functions.

FORM        |quod        |perfectum   |dicitur
:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |adjective   |verb
VERB-CLASS  |            |            |lexical
FINITENESS  |            |            |finite
GROUP       |            |            |
CLAUSE      |Carrier     |Attribute   |Head

FORM        |quod        |perfectum   |potest      |dici
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |adjective   |verb        |verb
VERB-CLASS  |            |            |auxiliary   |lexical
FINITENESS  |            |            |finite      |non-finite
GROUP       |            |            |Auxiliary   |Head
CLAUSE      |Carrier     |Attribute   |            |Head

FORM        |quod        |perfectum   |posse       |dici        |videtur
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |noun        |adjective   |verb        |verb        |verb
VERB-CLASS  |            |            |auxiliary   |lexical     |auxiliary
FINITENESS  |            |            |non-finite  |non-finite  |finite
GROUP       |            |            |Auxiliary2  |Head        |Auxiliary
CLAUSE      |Carrier     |Attribute   |            |Head

In the IP analysis, the verb *dico* is always a **(lexical verb)** and it functions as the head of the verbal group and of the clause. If the verbal group has no auxiliaries, it is **(finite)**. If the group has auxiliaries, the verb *dico* is **(non-finite)**. The **(finite auxiliary verb)** always functions as Auxiliary, and a **(non-finite auxiliary verb)** can function as Auxiliary2, Auxiliary 3, and so on depending on its other features and relative position. All functions are stable towards the classes of words and the lemmata acros examples.
  