# Ranks
The motivation for adding ranks to dependencies.

In the context of teaching Latin, wordings are represented as dependency structures for three end purposes:

* for students to view and learn the dependencies between words
* for automatically generating exercises on grammatical phenomena
* for assessing whether a clause can be understood given what was taught

Ranks will improve the usability of dependency trees for all these purposes, but here I shall focus on how they improve graphical representation of grammatical units and how this can improve comprehension.

# The clause

The first grammatical unit one has to master in a historical language is an affirmative clause representing a present process. One example of such a clause in Latin is *hoc vīnum suave est* (*this wine is smooth*). Variants of this clause include *hoc vīnum suave fit* (*this wine feels smooth*) and *hoc vīnum suāve ego faciō* (*this wine feels smooth to me*). Variants of the last include *an hoc vīnum suāve vōs facitis* (*does this wine feel smooth to you*) and *hoc vīnum suāve vōs oportet faciātis* (*notice how smooth this wine feels*). Here we see that we can create variants in English with similar grammatical structures only up to a certain point. From that point on we need two clauses in English (*notice* + *how smooth this wine feels*) for one in Latin (*hoc vīnum suāve vōs oportet faciātis*).

Languages differ in the amount of variation they offer for the rank of clauses and they differ in the words where this variation lies. Do we add operators like *an* or verbs like *does* to make an imperative clause become interrogative? Do we switch between two verbs like from *fit* to *faciō* to specify the person for whom something carries an attribute, or do we keep the verb *feels* and add an adjunct like *to me*? These are options we have within a given language to make meaning and they are not universal.

To understand which options one has at clause rank, students have to learn 'what goes instead of what' in a clause and this paradigmatic principle should be what guides what is a clause constituent. However, in dependency structure an argument such as *hoc vīnum* (*this wine*) depends on the process verb *faciātis* in the same way as the auxiliary verb *oportet*. This results in graphical representations like the following.

![Dependency without ranks](README/Dependency01.png "Dependency without ranks")

A better way for a learner to visualize this clause would be to separate the ranks at which dependencies occur. For instance, *hoc* depends on *vīnum* at the group rank. In the same way, *oportet* depends on *faciātis* at group rank. All other dependencies take place at clause rank. If dependencies are tagged for ranks, we are able to represent grammatical units at different ranks. At clause rank, we would have the following structure:

| hoc vīnum        | suāve            | vōs              | oportet faciātis |
|:----------------:|:----------------:|:----------------:|:----------------:| 
| Tail             | Tail             | Tail             | Head             | 

In turn, at the group rank we would have two structures:

| hoc              | vīnum            |
|:----------------:|:----------------:| 
| Tail             | Head             |

| oportet          | faciātis         |
|:----------------:|:----------------:| 
| Tail             | Head             |

Of course, since we are the ones who create the linguistic descriptions, we can ascribe different labels to each tail constituent of a grammatical unit such as Subject, Object, Carrier, Attribute, Attributor, and the like. And we can still represent clauses in the original format as shown above. The only thing that changes when adding the rank is that we are able to represent different sets of dependencies at different times and thus let students concentrate at different grammatical phenomena in each representation.

# The clause complex




