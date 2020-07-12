SELECT count(`id`), `class` FROM dependencies.`ittb-word`
WHERE `class` = 'adj'
GROUP BY `class`;

SELECT count(`id`), `class` FROM dependencies.`ip-word`
WHERE `class` = 'adjective'
GROUP BY `class`;

SELECT (100 * count(`id1`) / 2135), `class1`, `class2`, `function` FROM dependencies.`ittb-anchor`
WHERE `class1` = 'adj'
GROUP BY `class1`, `class2`, `function`;

SELECT (100 * count(`id1`)) / 1561, `class1`, `class2`, `function` FROM dependencies.`ip-anchor`
WHERE `class1` = 'adjective'
GROUP BY `class1`, `class2`, `function`;