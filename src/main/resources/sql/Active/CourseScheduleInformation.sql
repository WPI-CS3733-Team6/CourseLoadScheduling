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
LEFT JOIN course_information ci
ON se.course_num = ci.course_num
LEFT JOIN instructor_course_link_registered cart
ON se.id = cart.section_id
LEFT JOIN course_department_link cdl
ON ci.id = cdl.course_id
LEFT JOIN departments dept
ON cdl.dept_id = dept.id
WHERE se.id
IN(
  SELECT section_id
  FROM instructor_course_link_registered
  WHERE instructor_id
  IN(
    SELECT id
    FROM instructors
    WHERE user_id = ?
  )
)
AND (ci.course_num = se.course_num)
ORDER BY ci.course_name ASC;
