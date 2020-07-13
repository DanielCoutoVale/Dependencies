SELECT count(`id`), `class` FROM dependencies.`ittb-word`
WHERE `class` = 'adj'
GROUP BY `class`;#2135

SELECT count(`id`), `class` FROM dependencies.`ip-word`
WHERE `class` = 'adjective'
GROUP BY `class`;#1511

SELECT count(distinct(concat(`class2`,':',`function`))), `class1` FROM `ittb-anchor`
GROUP BY `class1`;

SELECT count(`id1`) AS `a-frequency`, (100 * count(`id1`)) / 2135 AS `r-frequency`, `class1`, `class2`, `function` FROM `ittb-anchor`
WHERE `class1` = 'adj'
GROUP BY `class1`, `class2`, `function`;

SELECT count(`id1`) AS `a-frequency`, (100 * count(`id1`)) / 1511 AS `r-frequency`, `class1`, `class2`, `function` FROM `ip-anchor`
WHERE `class1` = 'adjective'
GROUP BY `class1`, `class2`, `function`;

SELECT B.*, Ws.`form` as `wording` 
FROM `ip-word` W 
JOIN `word` W1 ON W.`id` = W1.`id` 
JOIN `wording` Ws ON Ws.`id` = W.`wording-id` 
JOIN `ittb-anchor` B ON B.`id1` = W.`id` 
LEFT JOIN `ip-anchor` A ON A.`id1` = W.`id` 
WHERE W.`class` = 'adjective' AND A.`id1` IS NULL;
