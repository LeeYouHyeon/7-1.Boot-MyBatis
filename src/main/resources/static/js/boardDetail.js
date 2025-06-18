const [modifyBtn, titleArea, contentArea, buttonArea] = ['modifyBtn', 'titleArea', 'contentArea', 'buttonArea'].map(e => document.getElementById(e));
const bno = document.getElementById('bno').value;

const title = titleArea.innerText, content = contentArea.innerText;
document.addEventListener('click', e => {
  switch(e.target.id) {
    case 'modifyBtn':
      titleArea.innerHTML = `<input type="text" class="form-control" id="t" placeholder="Title" name="title" value=${titleArea.innerText}>`;
      contentArea.innerHTML = `<textarea class="form-control" id="c" cols="30" rows="10" name="content" required>${contentArea.innerText}</textarea>`;
    
      buttonArea.innerHTML = `
      <button type="button" class="btn btn-secondary" id="cancelBtn">Cancel</button>
      <button type="submit" class="btn btn-warning" id="updateBtn">Update</button>`;
      break;
    case 'cancelBtn':
      titleArea.innerHTML = title;
      contentArea.innerHTML = content;
      buttonArea.innerHTML = `
      <button type="button" class="btn btn-outline-secondary" id="modifyBtn">Modify</button>
      <a th:href="@{/board/remove(bno=${bno})}">
          <button type="button" class="btn btn-outline-danger">Remove</button>
      </a>`;
      break;
    default: ;
  }
})