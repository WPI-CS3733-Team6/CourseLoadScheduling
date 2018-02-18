-- 3) Search Classes
-- A fast search for classes by: 
-- excpected popultaion, meeting days, course number, course name
-- and course status. 
SELECT
se.expected_pop,
sc.meeting_days,
cinfo.course_num, cinfo.course_name,
se.deleted

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_instance cinst
ON se.instance_id = cinst.id
LEFT JOIN course_information cinfo
ON cinst.course_id = cinfo.id
LEFT JOIN course_department_link cdl
ON cinfo.id = cdl.course_id
LEFT JOIN departments dept
ON cdl.dept_id = dept.id
WHERE ((cinst.term = :firstTerm OR cinst.term = :secondTerm) OR :firstTerm = 'false') -- need more than 1 for semester
AND (dept.dept_name = :deptName OR :deptTerm = 'false')
ORDER BY se.id ASC;
