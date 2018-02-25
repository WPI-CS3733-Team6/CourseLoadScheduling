-- 4) Advanced Search (Detailed)
-- A advanced search for the user to find classes by:
-- expected population, term, meeting days, course number,
-- course name and course status. All these fields are optional.
SELECT
sections.expected_pop, cinst.term,
schedule.meeting_days,
cinfo.course_num, cinfo.course_name,
sections.deleted

FROM course_information cinst
LEFT JOIN course_schedule sc
ON
LEFT JOIN course_sections se
ON 

WHERE ((se.term = :firstTerm OR se.term = :secondTerm) OR :firstTerm = 'false') -- need more than 1 for semester
AND (dept.dept_name = :deptName OR :deptName = 'false')
AND (se.expected_pop > :rangeStart OR se.expected_pop < :rangeEnd OR :rangeStart = 'false')
AND (ci.type = :courseType OR :courseType = 'false')
AND (sc.type = :sectionType OR :sectionType = 'false')
AND (ci.level = :level OR :level = 'false')
AND (sc.meeting_days = :days OR :days = 'false')
AND (ci.course_num = :courseNum OR :courseNum = 'false')

ORDER BY se.id ASC;
