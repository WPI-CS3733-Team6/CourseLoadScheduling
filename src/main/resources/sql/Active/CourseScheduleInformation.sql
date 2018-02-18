-- 6) Course Schedule information
-- Shows the courses information for schedule viewing includes:
-- course name, department, section number, meeting days and time
SELECT
ci.course_name,
se.section_num,
sc.meeting_days,
sc.time_start,
sc.time_end,
dept.dept_name

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_instance cinst
ON se.instance_id = cinst.id
LET JOIN course_information cinfo
ON cinst.course_id = cinfo.id
LEFT JOIN instructor_course_link_registered cart
ON cinst.id = cart.instance_id
LEFT JOIN course_department_link cdl
ON cinfo.id = cdl.course_id
LEFT JOIN departments dept
ON cdl.dept_id = dept.id
WHERE cinst.id
IN(
  SELECT instance_id
  FROM instructor_course_link_registered
  WHERE instructor_id
  IN(
    SELECT id
    FROM instructors
    WHERE user_id = :userId
  )
)
AND NOT instructor_course_link_registered.deleted;
ORDER BY ci.course_name ASC;
