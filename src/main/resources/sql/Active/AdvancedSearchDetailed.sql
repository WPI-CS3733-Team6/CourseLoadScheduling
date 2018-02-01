-- 4) Advanced Search (Detailed)
-- A advanced search for the user to find classes by:
-- expected population, term, meeting days, course number,
-- course name and course status. All these fields are optional.
SELECT
se.expected_pop, se.term,
sc.meeting_days,
ci.course_num, ci.course_name,
se.deleted

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_information ci
ON se.course_num = ci.course_num
LEFT JOIN instructor_course_link_cart cart
ON se.id = cart.section_id
LEFT JOIN course_department_link cdl
ON ci.id = cdl.course_id
LEFT JOIN departments dept
ON cdl.dept_id = dept.id
WHERE (se.term = ? OR ? = 'false') -- need more than 1 for semester
AND (dept.dept_name = ? OR ? = 'false')
AND (se.expected_pop > ? OR se.expected_pop < ? 'false')
AND (ci.type = ? OR ? = 'false')
AND (sc.type = ? OR ? = 'false')
AND (ci.level = ? OR ? = 'false')
AND (sc.meeting_days = ? OR ? = 'false')


ORDER BY se.id ASC;
