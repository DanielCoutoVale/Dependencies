SELECT A.`form1` AS `Carrier`, B.`form1` AS `Attribute`, A.`form2` AS `Process`, Ws.`form` AS `wording`
FROM (SELECT `form1`, `form2`, `id2` FROM dependencies.`ip-anchor`
WHERE `function` = 'Carrier') A
JOIN (SELECT `form1`, `form2`, `id2` FROM dependencies.`ip-anchor`
WHERE `function` = 'Attribute') B ON A.`id2` = B.`id2`
JOIN `word` W ON W.`id` = A.`id2` 
JOIN `wording` Ws ON Ws.`id` = W.`wording-id`;