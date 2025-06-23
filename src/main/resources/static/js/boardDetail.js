const [modifyBtn, titleArea, contentArea, buttonArea, fileUploadArea, commentArea] = ['modifyBtn', 'titleArea', 'contentArea', 'buttonArea', 'fileUploadArea', 'commentArea'].map(e => document.getElementById(e));
const bno = document.getElementById('bno').value;
const fileRemoveBtns = document.querySelectorAll('.fileRemoveBtn');

const title = titleArea.innerText, content = contentArea.innerText;
document.addEventListener('click', e => {
  switch(e.target.id) {
    case 'modifyBtn':
      titleArea.innerHTML = `<input type="text" class="form-control" id="t" placeholder="Title" name="title" value=${titleArea.innerText}>`;
      contentArea.innerHTML = `<textarea class="form-control" id="c" cols="30" rows="10" name="content" required>${contentArea.innerText}</textarea>`;
      for (const btn of fileRemoveBtns) btn.classList.remove('invisible');
      fileUploadArea.classList.remove("invisible");
      commentArea.classList.add('invisible');

      buttonArea.innerHTML = `
      <button type="button" class="btn btn-secondary" id="cancelBtn">Cancel</button>
      <button type="submit" class="btn btn-warning" id="regBtn">Update</button>`;
      return;
    case 'cancelBtn':
      titleArea.innerHTML = title;
      contentArea.innerHTML = content;
      buttonArea.innerHTML = `
      <button type="button" class="btn btn-outline-secondary" id="modifyBtn">Modify</button>
      <a th:href="@{/board/remove(bno=${bno})}">
          <button type="button" class="btn btn-outline-danger">Remove</button>
      </a>`;
      for (const btn of fileRemoveBtns) btn.classList.add('invisible');
      fileUploadArea.classList.add('invisible');
      commentArea.classList.remove('invisible');
      return;
    default: ;
  }

  if (e.target.classList.contains('fileRemoveBtn')) {
      const uuid = e.target.dataset.uuid;
      if(!confirm('파일을 삭제하시겠습니까?')) return;
      fetch('/board/removeFile/'+uuid, {
        method: 'delete'
      })
      .then(resp => resp.text())
      .then(isOk => {
        if (isOk == '1') {
          e.target.closest('.row').remove();
        } else {
          alert('삭제에 실패했습니다. 다시 시도해주세요.');
        }
      }).catch(console.log);
  }
})

// 댓글
const [cmtContent, cmtCancelBtn, cmtRegisterBtn, commentListArea] = ['cmtContent', 'cmtCancelBtn', 'cmtRegisterBtn', 'commentListArea'].map(e => document.getElementById(e));
// 1. 등록
if (cmtRegisterBtn != null) cmtRegisterBtn.addEventListener('click', () => {
  const content = cmtContent.value;
  if (content == '') {
    cmtContent.focus();
    return;
  }

  fetch('/comment/post', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify({
      bno: bno,
      writer: userEmail,
      content: content
    })
  }).then(resp => resp.text())
  .then(result => {
    if (result == '0') {
      alert('댓글 등록에 실패했습니다.');
   } else {
    cmtCancelBtn.click();
    loadComments();
   }
  }).catch(err => {
    console.log(err);
    alert('오류가 발생했습니다.');
  });
});

cmtCancelBtn.addEventListener('click', () => {
  cmtContent.value == '';
});

// 2. 불러오기
function loadComments(page = 1) {
  fetch(`/comment/${bno}/${page}`)
  .then(resp => resp.json())
  .then(json => {
    console.log(json);
    const {totalCount, commentVOList} = json;

    // commentListArea
    if (totalCount == 0) {
      commentListArea.innerHTML = '<div class="d-flex justify-content-center align-items-center">댓글이 없습니다.</div>';
      return;
    }
    let init = '<div class="row mb-3">';
      init += '<div class="col-2 d-flex justify-content-center align-items-center">작성자</div>';
      init += '<div class="col-6 d-flex justify-content-center align-items-center">내용</div>';
      init += '<div class="col-2 d-flex justify-content-center align-items-center">작성일</div>';
      init += `<div class="col-2 d-flex justify-content-center align-items-center">총 댓글 수 : ${totalCount}</div>`;
      init += '</div><hr>';
      commentListArea.innerHTML = init;
      for (const cvo of commentVOList) {
        const modifiable = cvo.writer == userEmail;
        const removable = modifiable;
        let cmt = `
        <div class="row mb-3" data-cno=${cvo.cno}>`;
        cmt += `
          <div class="col-2 d-flex justify-content-center align-items-center">${cvo.writer}</div>`;
        cmt += `
          <div class="col-6 d-flex align-items-center cmtContent">${cvo.content}</div>`;
        cmt += `
          <div class="col-6 d-none align-items-center cmtContentInput">
            <input type="text" class="form-control" data-cno=${cvo.cno} value=${cvo.content} />
          </div>`;
        cmt += `
          <div class="col-2 d-flex justify-content-center align-items-center">${cvo.regDate}</div>`;
        cmt += `
          <div class="col-2 d-flex justify-content-end">`;
        if (modifiable) {
          cmt += `
            <button type="button" class="btn btn-warning me-3 modifyBtn">수정</button>`;
          cmt += `
            <button type="button" class="btn btn-warning me-3 updateBtn d-none">확인</button>`;
        }
        if (removable) {
          cmt += `
            <button type="button" class="btn btn-danger removeBtn">삭제</button>`;
          cmt += `
            <button type="button" class="btn btn-secondary modCancelBtn d-none">취소</button>
          </div>`;
        }
        cmt += `
        </div>`;
        
        commentListArea.innerHTML += cmt;
      }

      // paging area
      const {startPage, endPage, pagingVO, prev, next} = json;
      let paging = `
      <ul class="pagination justify-content-center">`;
      paging += `
        <li class="page-item ${prev ? '' : 'disabled'} " data-page=${startPage - 1}>
          <span class="page-link">&laquo;</span>
        </li>
      `;
      for (let i = startPage; i <= endPage; i++) {
        paging += `
        <li class="page-item ${i == pagingVO.pageNo ? 'active' : ''}" data-page=${i}>
          <span class="page-link">${i}</span>
        </li>
        `;
      }
      paging += `
        <li class="page-item ${next ? '' : 'disabled'}" data-page=${endPage + 1}>
          <span class="page-link">&raquo;</span>
        </li>
      </ul>`;
      commentListArea.innerHTML += paging;
    }).catch(err => {
      console.log(err);
    });
}

loadComments();

document.addEventListener('click', e => {
  try {
    const comment = e.target.closest('.row');
    const cno = comment.dataset.cno;
    if (cno != undefined) {
      const [eachContent, cmtContentInput, modifyBtn, updateBtn, removeBtn, modCancelBtn] = ['cmtContent', 'cmtContentInput', 'modifyBtn', 'updateBtn', 'removeBtn', 'modCancelBtn'].map(e => comment.querySelector('.' + e));
      const defaultSet = [eachContent, modifyBtn, removeBtn];
      const modSet = [cmtContentInput, updateBtn, modCancelBtn];
      if (e.target.classList.contains('modifyBtn')) {
        defaultSet.map(c => {
          c.classList.add('d-none');
        });
        modSet.map(c => {
          c.classList.remove('d-none');
        });
      } else if (e.target.classList.contains('modCancelBtn')) {
        defaultSet.map(c => {
          c.classList.remove('d-none');
        });
        modSet.map(c => {
          c.classList.add('d-none');
        });
      } else if (e.target.classList.contains('removeBtn')) {
        if (!confirm('정말 댓글을 삭제하시겠습니까?')) return;
        fetch('/comment/' + cno, {
          method: 'delete'
        }).then(resp => resp.text())
        .then(result => {
          if (result == '0') {
            alert('댓글 삭제에 실패했습니다.');
            return;
          }
          loadComments();
        })
        .catch(err => {
          console.log(err);
          alert('오류가 발생했습니다.');
        })
      } else if (e.target.classList.contains('updateBtn')) {
        const input = cmtContentInput.querySelector('input');
        if (input.value == '') {
          input.focus();
          return;
        }
        fetch('/comment/update', {
          method: 'put',
          headers: {
            'Content-Type': 'application/json; charset=utf-8'
          },
          body: JSON.stringify({
            cno: cno,
            content: input.value
          })
        }).then(resp => resp.text())
        .then(result => {
          if (result == '0') alert('댓글 수정에 실패했습니다. 다시 시도해주세요.');
          else {
            eachContent.innerText = input.value;
            modCancelBtn.click();
          }
        }).catch(error => {
          console.log(error);
          alert('오류가 발생했습니다.');
        })
      }
    }
  } catch(ignore) {
    if (e.target.classList.contains('page-item') && !e.target.classList.contains('disabled')) {
      loadComments(e.target.dataset.page);
    }
  }
});