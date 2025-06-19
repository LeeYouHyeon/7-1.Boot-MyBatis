const [modifyBtn, titleArea, contentArea, buttonArea, fileUploadArea] = ['modifyBtn', 'titleArea', 'contentArea', 'buttonArea', 'fileUploadArea'].map(e => document.getElementById(e));
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