# Intelligentī Pauca
A systemic network for ranked universal dependencies

This section explains the translation of anchors for verbs.

For an introduction to IP, see [the motivation for IP](IP.md).

## Verbs
There are 60 anchors for verbs ('aux' or 'verb') in the ITTB corpus of which 2/3 (42) happen less than 1% of the time. Our goal is to reduce the number of anchors with frequency higher than 1% to under 10. To accommodate the relations between verbs, we shall cover five linguistic phenomena:

1. Auxiliaries versus process verbs 
2. Finitive versus infinitive verbs
3. Verb modes
4. Finite and non-finite clauses
5. Clause contacts

### Step 1

Let us start by focusing on verbal groups in which one verb represents a process with participants and the other verb modulates the statement. Here are some examples of the mapping between syntagma and structure for such cases in the ITTB corpus.

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

The examples above read *that in God there is no flaw*, *that in God there can be no evil*, *that in God nothing is violent*, and *that in God nothing can be violent*. The finite verb is always the head of the clause. In existential clauses, the noun is the subject of the verb *est* only when it is finite. If a finite verb such as *potest* is inserted, it becomes the subject of that verb instead. The infinitive verb is an 'Xcomp' of the finite verb. As for attributive clauses, the adjective is the head of the clause if there are no finite verbs. The noun is its subject and the auxiliary is its copula. When a finite verb such as *potest* is inserted, the noun becomes its subject and the adjective becomes its 'Xcomp'. As we can see, there is very little stability between word classes and the anchors associated with them.

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

In particular, there are auxiliary verbs such as *necesse est* and *possibile est*, which function like an attributive clause embedded in the verbal group. Here are the syntagmata and the structures according to UD and IP description.

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

The altered verbal group has an embedded clause. This dependency structures according to IP can be visualized in the following way:

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

With these changes, we achieve a full separation between auxiliary and lexical verbs.

### Step 2 



