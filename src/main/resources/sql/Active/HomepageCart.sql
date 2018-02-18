-- SIMILAR
-- Cart Query: Displays the courses names, course numbers
-- and meeting days where the instructor has a pending status.
SELECT
ci.course_name,
ci.course_num,
sc.meeting_days

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_information cinst
ON se.instance_id = cinst.id
LEFT JOIN course_information cinfo
ON cinst.course_id = cinfo.id
LEFT JOIN instructor_course_link_cart cart
ON cinst.id = cart.instance_id
WHERE cinst.id
IN(
  SELECT instance_id
  FROM instructor_course_link_cart
  WHERE instructor_id
  IN(
    SELECT id
    FROM instructors
    WHERE user_id = :userId
  )
)
AND (se.deleted = 'true')
AND (cart.status = 0)
ORDER BY se.id ASC;
