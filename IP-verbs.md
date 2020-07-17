# Intelligentī Pauca
A systemic network for ranked universal dependencies

This section explains the translation of anchors for verbs.

For an introduction to IP, see [the motivation for IP](IP.md).

## Verbs
There are 60 anchors for verbs ('aux' or 'verb') in the ITTB corpus of which 2/3 (42) happen for less than 1% of the verbs. Our goal is to reduce the number of anchors with frequency higher than 1% to under 10. To accommodate the relations between verbs, we shall cover five linguistic phenomena:

1. Auxiliary versus lexical verbs 
2. Finite versus non-finite verbs
3. Free versus bound clauses
4. Finite versus non-finite bound clauses
5. Embedded clauses
6. Predicated themes

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

There are four grammatical structures in this dependency tree and we can represent them graphically in the following way: 

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

Let us now move towards the other end of the verbal group and let us identify the last verb in that chain. We shall be using parsing results, not original texts for these examples. The original sentence from which these variants were created is *quod ... nec perfectum posse dici videtur* (*that does not seem to be possibly said perfect*).

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

The examples read *that is said perfect*, *that can be said perfect*, and *that seems to be possibly said perfect*. There are multiple issues with this analysis for both teaching and automatic learning. The first is the fact that, for each new verb inserted, the grammatical structure gets one level higher. The second is the fact that the pronoun *quod* (*that*) switches between being a subject and a passive subject depending on the lemma of the newly inserted finite verb, loosing its dependency on the previous verbs. The third is the fact that this pronoun serves as subject for a different lemma in each example. Finally, the attachments of the adjective *perfectus* and the verb *dico* are also unstable. The adjective is either attached to the verb *potest* or to the verb *dico* and the verb *dico* is either the head, an Xcomp of the verb *potest* or an Xcomp of the verb videtur. If this is the intended analysis, it is difficult to learn automatically. If it does not correspond to the annotator's intent, the parsing results are shown to be incorrect even for small grammatical variations. Either way, these structures are hard to use effectively for teaching Latin.

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
FINALITY    |            |            |non-finite  |non-finite  |finite
GROUP       |            |            |Auxiliary2  |Head        |Auxiliary
CLAUSE      |Carrier     |Attribute   |            |Head

In the IP analysis, the verb *dico* is always a **(lexical verb)** and it functions as the head of the verbal group and of the clause. If the verbal group has no auxiliaries, it is **(finite)**. If the group has auxiliaries, the verb *dico* is **(non-finite)**. The **(finite auxiliary verb)** always functions as Auxiliary, and a **(non-finite auxiliary verb)** can function as Auxiliary 2, Auxiliary 3, and so on depending on its other features and relative position to other words. All functions are stable towards the classes of words and the lemmata across examples.

### Step 3

Up to now, we have described only verbal groups that could potentially be found in a free declarative clause, a clause that can be sent as a complete message on its own. There is another mode of representing a process, namely representing it as something that takes place together with another process. When a clause represents a process in such a way, it cannot be sent on its own as a message. It must accompany a free clause. One way whereby a clause can have a 'bound' status is by having a marker such as *ut* (*so that*) and *sic* (*in this manner*). Another way whereby it can be bound is by having a conjunctive verbal group.

A conjunctive verbal group differs from an non-conjunctive verbal group in form. In Latin, this difference concentrates in the finite verb. Here are some examples of verbal groups with and without auxiliaries. Notice that only the finite verbs vary between non-conjunctive and conjunctive verbal groups in the examples.

--          |non-conjunctive   |conjunctive
:----------:|:----------------:|:---------------:
past        |similata sunt     |similarentur
present     |similantur        |similentur
future      |similabuntur      |similentur

--          |non-conjunctive   |conjunctive
:----------:|:----------------:|:---------------:
past        |poterant similari |possent similari
present     |possunt similari  |possint similari
future      |poterunt similari |possint similari

Let now turn our attention to the differences between UD and IP analyses regarding this linguistic phenomenon. As a note, the adjunct *de facili* (*easily*) was removed from the original clause in the first example to make it shorter.

FORM        |ut          |sic         |omnes       |possent     |divinae     |cognitionis |participes  |esse
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |adv         |            |verb        |            |            |            |aux            
VERBFORM    |            |            |            |fin         |            |            |            |inf
MOOD        |            |            |            |sub         |            |            |            |
STRUCTURE-1 |            |            |            |            |            |            |Head        |Cop
STRUCTURE-2 |Mark        |Advmod      |            |Head        |            |            |Xcomp       |

FORM        |ut          |ei          |secundum    |formas      |multiplices |aliqua      |similari    |possint
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |            |            |            |            |            |verb        |verb
MOOD        |            |            |            |            |            |            |inf         |subj
STRUCTURE   |Mark        |            |            |            |            |            |Xcomp       |Head

FORM        |ut          |sic         |omnes       |possent     |divinae     |cognitionis |participes  |esse
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |conjunction |adverb      |            |verb        |            |            |            |verb
VERB-CLASS  |            |            |            |auxiliary   |            |            |            |lexical
FINITENESS  |            |            |            |finite      |            |            |            |non-finite
FINITE-MODE |            |            |            |conjunctive |            |            |            |
GROUP       |            |            |            |Auxiliary   |            |            |            |Head
CLAUSE      |Marker      |Marker      |            |            |            |            |            |Head

FORM        |ut          |ei          |secundum    |formas      |multiplices |aliqua      |similari    |possint            
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |conjunction |            |            |            |            |            |verb        |verb
VERB-CLASS  |            |            |            |            |            |            |lexical     |auxiliary
FINITENESS  |            |            |            |            |            |            |non-finite  |finite
FINITE-MODE |            |            |            |            |            |            |            |conjunctive
GROUP       |            |            |            |            |            |            |Head        |Auxiliary
CLAUSE      |Marker      |            |            |            |            |            |Head        |

Examples read *so that in this manner every one could be a participant in the knowledge of God* and *so that something can be learned about Him from his multiple forms*. The text analyses of these two examples are similar regarding the verbs that are considered conjunctive/subjunctive. However, the two analyses do not always coincide. Let us consider the following example:

FORM        |dum         |unum        |eorum       |movetur
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |sconj       |            |            |verb
MOOD        |            |            |            |ind
STRUCTURE   |Mark        |            |            |Head

FORM        |dum         |unum        |eorum       |movetur            
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |conjunction |            |            |verb
VERB-CLASS  |            |            |            |lexical
FINITENESS  |            |            |            |finite
FINITE-MODE |            |            |            |conjunctive
GROUP       |            |            |            |Head
CLAUSE      |Marker      |            |            |Head

The example reads *while one of them moves/moved*. It differs from *unum eorum movetur* (*one of them is moving*) because it represents a process happening at any time, while the latter represents a process happening now. In the UD analysis, the verb *movetur* is indicative and not subjunctive. All 1st-paradigm unbranched ō-aspect verbs (*moveor*, *moveris*, *movetur*, *movemur*, *movemini*, *moventur*, *moveo*, *moves*, *movet*, *movemus*, *movetis*, *movent*) are always indicative, thus not subjunctive in an UD analysis. In contrast, in the IP description, the conjunctive mode is a mode of representing a process, a way of representing it, different from other modes. In that sense, we would have the following three modes:

--          |non-conjunctive  |ut-conjunctive   |dum-conjunctive     
:----------:|:---------------:|:---------------:|:---------------:
past        |motum est        |moveretur        |movetur
present     |movetur          |moveatur         |movetur
future      |movebitur        |moveatur         |movetur

Notice that the verb *movetur* (*is moving* or *moves/moved*) can be either an option in a system of three options including 'past', 'present', and 'future' or the only option of the system. These modes of representing a process are what counts in an IP analysis. As a result, the feature 'conjunctive' can only be determined given the context of wording and a tagger must take that context into account. In turn, once a verb is declared 'conjunctive' by a tagger, it _**cannot**_ be the root of a message. If it is declared 'non-conjunctive', it will compete with the other non-conjunctive verbs for that title.

The IP analysis also differs from the UD analysis regarding the other verbs declared 'non-conjunctive'. In the IP analysis all verbs that are not conjunctive are non-conjunctive and a **(non-conjunctive verb)** is enough evidence for the corresponding **(lexical verb)** to be a candidate root for the wording. In the UD analysis, this is not the case. Besides the subjunctive mood ("Sub"), there are two other moods: namely the indicative ("Ind") and the imperative ("Imp"). This means that the fact that **(indicative verbs)** are often roots will not be transferred to **(imperative verbs)**, which are rare in this corpus. In the IP description, the information whether a verb is indicative or imperative is kept not in the system of FINITE-MODE, but in a separate one: namely, that of NON-CONJUNCTIVE-MODE.

### Step 4

Now we shall move on to the differences between finite and non-finite clauses and how these differences affect parsing results. As we saw in the previous step, only free clauses (non-conjunctive) can be sent on their own and only finite clauses can be free. As for conjunctive clauses, they include both those that have a conjunctive finite verb and those that have a finite verb and a conjunctive marker. In this section, we shall see another type of bound clauses, namely those whose subject is represented by the other clause that they are bound to.

Finite verbal groups are those verbal groups that can occur in clauses that have an explicit subject. In this sense, both the verbal group *est* in *suum intelligere est simplex* (*his intelligence is simple*) and the verbal group *esse* in the wording *sci suum intelligere esse simplicem* (*know that his intelligence is simple*) are finite verbal groups. The verbal group *esse* is conjunctive, thereby being necessarily in a bound clause; whereas the verbal group *est* is non-conjunctive, thereby being in a free clause unless other markers are inserted.

Here we find another context-based classification. If a finite verbal group is a verbal group that makes a clause be finite and not all finite clauses are free clauses as we pointed out above, we need to account for the fact that re-branch verbs such as *esse*, *similari*, and *similare* can be either a finite verb as in *esse* or a non-finite verb as in *potest esse* or *posse esse*. Since this difference can be detected by a tagger based on the context of wording, we can let the tagger distinguish whether a particular occurrence of *esse* is finite or not.

In the ITTB corpus, there are three styles of non-finite bound clause:

1. *ad* + andum (*circa*)
    - *ad [ eorum errores ] destruendos* (*for destroying their errors*)
    - *ad sciendum* (*for knowing*)
2. *in* + endo (*ab*)
    - *in [ rebus ] cognoscendis* (*in getting to know things*)
    - *in judicando* (*in judging*)
3. endo
   - *[ temporalibus ] administrandis* (*by governing the temporary things*)
   - *agendo* (*by acting*)

These verbs are non-finite and they agree with the object if the verb is transitive. The subject in non-finite clauses is always missing and it can be recovered from the other clause to which these are bound. Here we must be aware that Latin had a larger variety of non-finite clauses including *ab [ urbe ] condita* (*since founding the city*), *usque ad [ haec ] facienda* (*until doing that*), *inter [ haec ] facienda* (*while doing that*), *post [ haec ] facta* (*after doing that*), *ante [ haec ] facienda* (*before doing that*), and so on. A parser created with analyses of ITTB corpus will not parse these other non-finite clauses correctly.

That said, the analysis of non-finite clauses differ significantly from UD to IP descriptions. Here are two examples for each.

FORM        |ad          |eorum       |errores     |destruendos
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adp         |pron        |noun        |verb
VERB-FORM   |            |            |            |gdv
CASE        |            |gen         |acc         |gen
STRUCTURE   |Mark        |            |Subj-Pass   |Head

FORM        |ad          |sciendum
:----------:|:----------:|:----------:
WORD-CLASS  |adp         |verb
VERB-FORM   |            |ger
CASE        |            |acc
STRUCTURE   |Mark        |Head

FORM        |ad          |eorum       |errores     |destruendos
:----------:|:----------:|:----------:|:----------:|:----------:
WORD-CLASS  |adposition  |noun        |noun        |verb
VERB-CLASS  |            |            |            |lexical
FINITENESS  |            |            |            |non-finite
SEAMEDNESS  |            |            |            |seamed
VERB-CORE   |            |            |            |ō-nd-core
VERB-FOLIAGE|            |            |            |am-foliage
VERB-SEAM   |            |            |            |ae/ās-seam
CASE        |            |genitive    |accusative  |
CLAUSE      |Marker      |            |Goal        |Head

FORM        |ad          |sciendum
:----------:|:----------:|:----------:
WORD-CLASS  |adposition  |verb
VERB-CLASS  |            |lexical
FINITENESS  |            |non-finite
SEAMEDNESS  |            |unseamed
VERB-CORE   |            |ō-nd-core
VERB-FOLIAGE|            |am-foliage
VERB-SEAM   |            |
CASE        |            |
CLAUSE      |Marker      |Head

In the UD description, *destruendos* and *sciendum* are two different verb forms, namely 'gerundive' and 'gerund'. No transfer is enabled from one to the other. However, transfer is enabled between gerundive verbs or gerund verbs in different cases. In the IP description, *destruendos* and *sciendum* are both non-finite, thus they have no subject. Both of them share the remainder of their attachments with their respective finite counterparts. If they agree with the object, they are 'seamed'. If there is no object for them to agree with, they are 'unseamed'. This opposition between seamed and unseamed verbs shall do the opposition between 'gerundive' and 'gerund' verbs. In addition, this opposition will enable the parser to transfer some rules to non-finite clauses with other verbs such as *destructos* and *scitum* in case the corpus has some of them. Finally, the unseamed verb consists a single core composed of an ō-aspect stem, an nd-branch and an um-leaf whereas the seamed verb is composed of a core and a seam: the core is composed of an ō-aspect stem, an nd-branch while the seam is one of six leaves from the am-foliage. With this further formal specification, the verb does not need to agree with the object in case. This forces the parser to learn the nominal case as dependent of the verb: nominative subject for *destruit*, accusative subject for *destruere*, but no subject for *destruendos* and *destruendis* (non-finite); and accusative object for *destruit*, *destruere*, and *destruendos*, but ablative object for *destruendis*. These rules for nominal case will apply across all verbs whether or not the noun is preceded by an adposition. In turn, this is likely to improve rule transfer.

### Step 5

Now we shall turn our attention to references to participants of a process and references to the process itself.

1. Operative participle
    - *esse [[ movens immobile ]]*
    - *esse [[ quod immobile movet ]]*
    - *hoc esse [[ movens immobile ]]*
    - *hoc esse [[ quod immobile movet ]]*
    - *hoc [[ movens immobile ]]*
    - *hoc [[ quod immobile movet ]]*
    - *[[ movens immobile ]]*
    - *[[ quod immobile movet ]]*
2. Passive participle
    - *esse [[ motum a deo ]]*
    - *esse [[ quod a deo movetur ]]*
    - *hoc esse [[ motum a deo ]]*
    - *hoc esse [[ quod a deo movetur ]]*
    - *hoc [[ motum a deo ]]*
    - *hoc [[ quod a deo movetur ]]*
    - *[[ motum a deo ]]*
    - *[[ quod a deo movetur ]]*
3. Actor
    - *motor [[ immobilis ]]*
    - *hoc motor [[ immobilis ]]*
4. Goal
    - *motum [[ dei ]]*
    - *hoc motum [[ dei ]]*
5. Process as thing
    - *movere [[ dei immobilis ]]*
    - *hoc movere [[ dei immobilis ]]*

### Step 6

FORM        |huiusmodi   |autem       |est         |hoc         |quod        |dicimus     |deum        |esse 
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:


FORM        |et          |sic         |nihil       |inconveniens|accidit     |ponentibus  |deum        |non         |esse: 
:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:

The examples read *so this is the way we say god is* and *and so there is no inconvenient, which makes god be*.





 


 
