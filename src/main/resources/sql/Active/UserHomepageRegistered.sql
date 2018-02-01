--- Group 6


-- 1) User Homepage
-- Registered Query: Displays the courses names, course numbers
-- and meeting days where the instructor is registered in.
SELECT
ci.course_name,
ci.course_num,
sc.meeting_days

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_information ci
ON se.course_num = ci.course_num
LEFT JOIN instructor_course_link_registered cart
ON se.id = cart.section_id
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
ORDER BY se.id ASC;
