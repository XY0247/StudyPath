function DeptStudentClick(element)
{
    const info = element.getAttribute('data-info');
    // 发送 Cookie
    document.cookie = "DeptStudent="+ info;
    // alert(info)
// 显示：name=oeschger;favorite_food=tripe
}

function submitAddCourseOffingForm(event){
    const courseOffingData = {
        courseId:  document.getElementById('courseId').value,
        semester: document.getElementById('semester').value,
        year: Number(document.getElementById('year').value),
        instructorId: document.getElementById('instructorId').value,
        maxEnrollment: Number(document.getElementById('max').value),
        currentEnrollment: 0,
    };
    const url = '/StudyPath/AddCourseOffering';
    submitForm(event, courseOffingData, url, "AddCourseOfferingMessage", "AddCourseOfferingHref");
}
function submitEditCourseOffingForm(event){
    const courseOffingData = {
        offeringId: Number(document.getElementById('courseOfferingId').innerText),
        courseId:  document.getElementById('courseId').value,
        semester: document.getElementById('semester').value,
        year: Number(document.getElementById('year').value),
        instructorId: document.getElementById('instructorId').value,
        maxEnrollment: Number(document.getElementById('max').value),
        currentEnrollment: 0,
    };
    const url = '/StudyPath/EditCourseOffering';
    submitForm(event, courseOffingData, url, "EditCourseOfferingMessage", "EditCourseOfferingHref");
}
function submitEditStudentForm(event) {
    const studentData = {
        name:  document.getElementById('StudentName').value,
        no: document.getElementById('StudentId').innerText,
        sex: document.getElementById('StudentSex').value,
        age: Number(document.getElementById('StudentAge').value),
        dept: document.getElementById('StudentDept').value,
        password: document.getElementById('StudentPwd').value,
        phone: document.getElementById('StudentPhone').value,
        level: 1,
    };
    const url = '/StudyPath/EditStudent';
    submitForm(event, studentData, url, "EditStudentMessage", "EditStudentHref");
}
function submitAddStudentForm(event) {
    const studentData = {
        name:  document.getElementById('StudentName').value,
        no: document.getElementById('StudentId').value,
        sex: document.getElementById('StudentSex').value,
        age: Number(document.getElementById('StudentAge').value),
        dept: document.getElementById('StudentDept').value,
        password: document.getElementById('StudentPwd').value,
        phone: document.getElementById('StudentPhone').value,
        level: 1,
    };
    const url = '/StudyPath/AddStudent';
    submitForm(event, studentData, url, "AddStudentMessage", "AddStudentHref");
}
function submitEditCourseForm(event) {
    const courseData = {
        no: document.getElementById('CourseId').innerText,
        name: document.getElementById('CourseName').value,
        pno: document.getElementById('CoursePid').value||null,
        credit: Number(document.getElementById('CourseCredit').value),
        type: document.getElementById('CourseType').value
    };
    const url = '/StudyPath/EditCourse';
    submitForm(event, courseData, url, "EditCourseMessage", "EditCourseHref");
}
function submitEditTeacherForm(event) {
    const teacherData = {
        no:  document.getElementById('TeacherID').innerText,
        name: document.getElementById('TeacherName').value,
        sex: document.getElementById('TeacherSex').value,
        age: Number(document.getElementById('TeacherAge').value),
        teb: document.getElementById('TeacherTeb').value,
        tpt: document.getElementById('TeacherTpt').value,
        password: document.getElementById('TeacherPwd').value,
        level: 2,
    };
    const url = '/StudyPath/EditTeacher';
    submitForm(event, teacherData, url, "EditTeacherMessage", "EditTeacherHref");
}
function submitAddTeacherForm(event) {
    const teacherData = {
        no:  document.getElementById('TeacherID').value,
        name: document.getElementById('TeacherName').value,
        sex: document.getElementById('TeacherSex').value,
        age: Number(document.getElementById('TeacherAge').value),
        teb: document.getElementById('TeacherTeb').value,
        tpt: document.getElementById('TeacherTpt').value,
        password: document.getElementById('TeacherPwd').value,
        level: 1,
    };
    const url = '/StudyPath/AddTeacher';
    submitForm(event, teacherData, url, "AddTeacherMessage", "AddTeacherHref");
}
function submitAddCourseForm(event) {
    const courseData = {
        no: document.getElementById('CourseId').value,
        name: document.getElementById('CourseName').value,
        pno: document.getElementById('CoursePid').value||null,
        credit: Number(document.getElementById('CourseCredit').value),
        type: document.getElementById('CourseType').value
    };
    const url = '/StudyPath/AddCourse';
    submitForm(event, courseData, url, "AddCourseMessage", "AddCourseHref");
}
function submitEditDepartmentForm(event) {
    const courseData = {
        no: document.getElementById('departmentID').value,
        name: document.getElementById('departmentName').innerText,
        manager:document.getElementById('departmentManager').value,
    };
    const url = '/StudyPath/EditDepartment';
    submitForm(event, courseData, url, "EditDepartmentMessage", "EditDepartmentHref");
}
function submitAddDepartmentForm(event) {
    const courseData = {
        no: document.getElementById('departmentID').value,
        name: document.getElementById('departmentName').value,
        manager:document.getElementById('departmentManager').value,
    };
    const url = '/StudyPath/AddDepartment';
    submitForm(event, courseData, url, "AddDepartmentMessage", "AddDepartmentHref");
}
function submitForm(event, formData, url, messageElementId, hrefElementId) {
    // 将对象转换为 JSON 字符串
    const jsonString = JSON.stringify(formData);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonString // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            // 显示消息
            document.getElementById(messageElementId).innerHTML = result.message;

            // 如果修改成功，更新页面链接
            if (result.message.indexOf("成功")!==-1) {
                document.getElementById(hrefElementId).href = result.url;
            }
            else
                document.getElementById(hrefElementId).href = 'index';
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}

function doDepStudent(element) {
    const Value = element.getAttribute('deptStudent');
    // 直接跳转到新的 URL
    window.location.href = `/StudyPath/StudentInfo?deptStudent=${Value}`;
}

function doDelStudent(element) {
    fetch('/StudyPath/StudentInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            studentNo: element.getAttribute("delStudent"),
        }) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            alert(result.message);
            window.location.href = `/StudyPath/StudentInfo?deptStudent=`+element.getAttribute("dept");
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}
function doDelTeacher(element) {
    fetch('/StudyPath/TeacherInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            teacherNo: element.getAttribute("delTeacher"),
        }) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            alert(result.message);
            window.location.href = `/StudyPath/TeacherInfo`;
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}
function doDelCourse(element) {
    const searchParams = new URLSearchParams(window.location.search);
    fetch('/StudyPath/CourseInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            courseNo: element.getAttribute("delCourse"),
        }) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            alert(result.message);
            window.location.href = `/StudyPath/CourseInfo`;
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}
function doDelCourseOffering(element) {
    fetch('/StudyPath/CourseOfferingInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            courseOfferingNo: Number(element.getAttribute("delCourseOffering")),
        }) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            alert(result.message);
            window.location.href = `/StudyPath/CourseOfferingInfo`;
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}
function doDelDepartment(element) {
    fetch('/StudyPath/DepartmentInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            departmentName: element.getAttribute("delDepartment"),
        }) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            alert(result.message);
            window.location.href = `/StudyPath/DepartmentInfo`;
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}
function doEditStudent(element) {
    const Value = element.getAttribute('editStudent');
    window.location.href = `/StudyPath/EditStudent?editStudent=${Value}`;
}
function doEditCourse(element) {
    const Value = element.getAttribute('editCourse');
    window.location.href = `/StudyPath/EditCourse?editCourse=${Value}`;
}
function doEditCourseOffering(element){
    const Value = element.getAttribute('editCourseOffering');
    window.location.href = `/StudyPath/EditCourseOffering?editCourseOffering=${Value}`;
}
function doViewCourseOfferingDetails(element) {
    const Value = element.getAttribute('viewCourseOffering');
    window.location.href = `/StudyPath/ViewCourseOffering?viewCourseOffering=${Value}`;
}

function doViewCourseOffering(element) {
    const Value = element.getAttribute('viewCourseOffering');
    window.location.href = `/StudyPath/TeacherCourse?viewCourseOffering=${Value}`;
}

function doEditDepartment(element){
    const Value = element.getAttribute('editDepartment');
    window.location.href = `/StudyPath/EditDepartment?editDepartment=${Value}`;
}

function doEditTeacher(element){
    const Value = element.getAttribute('editTeacher');
    window.location.href = `/StudyPath/EditTeacher?editTeacher=${Value}`;
}

function doEditTeacherCourse(element){
    const Value = element.getAttribute('teacherCourseID');
    window.location.href = `/StudyPath/TeacherCourseSetting?teacherCourseID=${Value}`;
}
function postStudentCourseSelection(element){
    postStudentCourse(element,'/StudyPath/StudentCourseSelection');
}
function postStudentCourseDrop(element) {
    postStudentCourse(element,'/StudyPath/StudentCourseDrop');
}
function postStudentSetting(element) {
    const value = {
        enrollmentId: element.getAttribute('enrollmentId'),
        CourseGrade: document.getElementById('CourseGrade').value,
        viewCourseOffering : element.getAttribute('viewCourseOffering'),
    }
    fetch('/StudyPath/TeacherCourseSetting', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(value) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            // 显示消息
            document.getElementById('TeacherCourseSettingMessage').innerHTML = result.message;

            // 如果修改,更新页面链接
            document.getElementById('TeacherCourseSettingHref').href = result.url;
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}

function postStudentCourse(element,url) {
    const value = {
        OfferingId: element.getAttribute('OfferingId')
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(value) // 使用转换后的 JSON 字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('网络响应不正常');
            }
            return response.json();
        })
        .then(result => {
            // 显示消息
            document.getElementById('StudentCourseMessage').innerHTML = result.message;

            // 如果修改成功，更新页面链接
            if (result.message.indexOf("成功")!==-1) {
                document.getElementById('StudentCourseHref').href = result.url;
            }
            else
                document.getElementById('StudentCourseHref').href = '/StudyPath/StudentCourse';
        })
        .catch(error => {
            alert('错误: ' + error);
        });
}