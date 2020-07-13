SELECT count(`id`), `class` FROM dependencies.`ittb-word`
WHERE `class` = 'aux' OR `class` = 'verb'
GROUP BY `class`;#1318+4663

SELECT count(`id`), `class` FROM dependencies.`ip-word`
WHERE `class` = 'verb'
GROUP BY `class`;#1511

SELECT count(`id1`) AS `a-frequency`, (100 * count(`id1`)) / (1318+4663) AS `r-frequency`, `class1`, `class2`, `function` FROM `ittb-anchor`
WHERE `class1` = 'aux' OR `class1` = 'verb'
GROUP BY `class1`, `class2`, `function`;

SELECT count(`id1`) AS `a-frequency`, (100 * count(`id1`)) / 1511 AS `r-frequency`, `class1`, `class2`, `function` FROM `ip-anchor`
WHERE `class1` = 'verb'
GROUP BY `class1`, `class2`, `function`;