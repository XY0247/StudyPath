### 1. 学生表 (Students)

- **student_id** (主键, INT, 自增) - 学生唯一标识
- **name** (VARCHAR(100)) - 学生姓名
- **gender** (ENUM('男', '女')) - 性别
- **birth_date** (DATE) - 出生日期
- **email** (VARCHAR(100), UNIQUE) - 电子邮件
- **phone_number** (VARCHAR(15)) - 电话号码
- **enrollment_year** (YEAR) - 年级
- **department_id** (外键, INT) - 关联系别表

### 2. 课程表 (Courses)

- **course_id** (主键, INT, 自增) - 课程唯一标识
- **course_name** (VARCHAR(100)) - 课程名称
- **course_code** (VARCHAR(10), UNIQUE) - 课程代码
- **credits** (INT) - 学分
- **semester** (ENUM('秋季', '春季', '夏季')) - 学期
- **instructor_id** (外键, INT) - 关联教师表
- **max_enrollment** (INT) - 最大选课人数

### 3. 选课表 (Enrollments)

- **enrollment_id** (主键, INT, 自增) - 选课记录唯一标识
- **student_id** (外键, INT) - 学生ID，关联到学生表
- **course_id** (外键, INT) - 课程ID，关联到课程表
- **enrollment_date** (DATETIME) - 选课日期
- **grade** (VARCHAR(2)) - 成绩（如：A、B、C、D、F等）

### 4. 教师表 (Instructors)

- **instructor_id** (主键, INT, 自增) - 教师唯一标识
- **name** (VARCHAR(100)) - 教师姓名
- **department_id** (外键, INT) - 所属系别，关联系别表
- **email** (VARCHAR(100), UNIQUE) - 电子邮件
- **phone_number** (VARCHAR(15)) - 电话号码

### 5. 系别表 (Departments)

- **department_id** (主键, INT, 自增) - 系别唯一标识
- **department_name** (VARCHAR(100)) - 系别名称
- **head_of_department** (VARCHAR(100)) - 系主任姓名

### 6. 课程先修表 (Prerequisites)

- **prerequisite_id** (主键, INT, 自增) - 先修课程记录唯一标识
- **course_id** (外键, INT) - 课程ID，关联到课程表
- **prerequisite_course_id** (外键, INT) - 先修课程ID，关联到课程表

### 关系说明

- 学生表与选课表之间是一对多的关系（一个学生可以选多门课）。
- 课程表与选课表之间是一对多的关系（一个课程可以被多个学生选）。
- 教师表与课程表之间是一对多的关系（一个教师可以教授多门课程）。
- 系别表与教师表之间是一对多的关系（一个系别可以有多个教师）。
- 课程先修表用于管理课程之间的先修关系。

### 示例查询

- 查询某个学生的所有选课信息及成绩。
- 查询某门课程的所有选课学生及其成绩。
- 查询某个教师教授的所有课程及其选课人数。
- 查询某个系别的所有教师及其教授的课程。

这个设计方案提供了一种更全面的学生选课系统结构，可以根据实际需求进行进一步的调整和扩展。希望对你有帮助！