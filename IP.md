# Intelligentī Pauca
A systemic network for ranked universal dependencies

Latin corpora such as ITTB, AGLDT, and PROIEL have been annotated with classes and functions from a cross-linguistic description called **Universal Dependencies** (UD). These classes and functions are meant to be universal across all languages. However, despite the enormous impact this universality has to the comparability of study results as one can easily see at [UDPipe linguistic model comparison page](http://ufal.mff.cuni.cz/udpipe/models), grammatical structures in this linguistic description lack a grounding on both semantics and expression.

From a pedagogical perspective, the fact that grammatical similarities between examples do not reflect semantic similarities in this linguistic description can render the grammatical structures less useful for teaching Latin. In this regard, when looking up dependency trees, Latin learners must learn how to map different grammatical structures onto the same semantic structure and vice versa. This effort might be (or be perceived as) larger than the effort of learning the grammatical structures directly from text without looking up dependency trees.

From a computational perspective, the fact that grammatical similarities between examples do not reflect their formal similarities makes this linguistic description hard to learn automatically from corpora. In particular, this low learnability adds a toll to the reliability of parsers for languages such as Latin for which linguistic data is scarce.

In this step-by-step translation documentation, I shall illustrate the issues I am referring to regarding low meaningfulness and low learnability and demonstrate how to solve them by translating current text analyses from Universal Dependencies into a new Latin description that I am calling **Intelligentī Pauca** (IP), which draws concepts from [Halliday's Introduction to Functional Grammar](https://www.routledge.com/Hallidays-Introduction-to-Functional-Grammar/Halliday-Matthiessen/p/book/9781444146608?gclid=CjwKCAjwgdX4BRB_EiwAg8O8Hc2VVdNbBwBYR_MrLi65QC-PLlDbjjuiPMI4DJ7AxS2HetDRM8iuaBoCS0wQAvD_BwE). In the next section, I shall present the issue regarding low learnability in more detail. And in the remainder of this step-by-step translation documentation, I shall present how to improve the learnability of structures for four parts of speech: namely, adjectives, verbs, nouns, and conjunctions.

## Learnability

Statistical parsers often rely on the sequence of word classes/features to determine 1) which word depends on which (**attachment**) and 2) which role the tail word plays relative to the head one (**labeling**). This means that a linguistic description that results in less attachment options and less labeling options for the same sequence of word classes will be learned with less data than a linguistic description that results in more options. Taking this into consideration, let us take a look at the five most frequent attachment and labeling options (**anchors**) that exist for a tail adjective in ITTB dev corpus.

A-Frequency |R-Frequency |Tail class  |Head class  |Dependency   
:----------:|:----------:|:----------:|:----------:|:----------:
1041        |48.76%      |adj         |noun        |Amod
97          |04.54%      |adj         |verb        |Xcomp
80          |03.75%      |adj         |verb        |Obl
70          |03.28%      |adj         |verb        |Amod
64          |03.00%      |adj         |adj         |Conj

As we can see in the table above, adjectives play various roles relative to words of various classes in ITTB. However, half of the dependencies are attachments to a noun with the label "Amod". In total, the current annotation of ITTB has 62 anchors for adjectives, half of which occur less than 5 times (less than 0.19%). Such an annotation is much more difficult to learn than one that has a smaller number of anchors with similar frequencies. In the following, I shall demonstrate that reducing the number of attachment and labeling options is not only doable, but also that by doing so the parsing results shall better reflect semantic similarities, thus becoming more useful for multiple tasks. In the following sections, I show how each part of speech was reinterpreted and how structures can be made more similar across multiple examples representing similar phenomena.

* [Adjectives](IP-adjectives.md)
* [Verbs](IP-verbs.md)
* [Nouns](IP-nouns.md)
* [Conjunctions](IP-conjunctions.md)



