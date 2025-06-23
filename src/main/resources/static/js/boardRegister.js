console.log("boardRegister.js in")

const [trigger, fileInput, fileZone] = ['trigger', 'file', 'fileZone'].map(e => document.getElementById(e));

trigger.addEventListener('click', () => {
fileInput.click();
});

// 실행파일 막기 / 5MB 이상 파일 막기
const regExp = new RegExp("\.(exe|jar|msi|dll|sh|bat)$");
const maxSize = 1024*1024*5;

function fileValidation(fileName, fileSize) {
  if (regExp.test(fileName)) return 0;
  if (fileSize > maxSize) return 0;
  return 1;
}

fileInput.addEventListener('change', () => {
  let isOk = 1; // 검증을 통과했는지의 여부

  let ul = '<ul class="list-group list-group-flush">';
  for (const file of fileInput.files) {
    const validResult = fileValidation(file.name, file.size);
    isOk *= validResult;
    let li = '<li class="list-group-item">';
    li += '<div class="mb-3">';
    li += `<div class="fw-bold text-${validResult ? 'success' : 'danger'}">업로드 ${validResult ? '' : '불'}가능</div>`;
    li += `${file.name}</div>`;
    li += `<span class="badge text-bg-${validResult ? 'success' : 'danger' }">${file.size} Bytes</span>`;
    li += '</li>';
    ul += li;
  }
  ul += '</ul>';
  fileZone.innerHTML = ul;

  document.getElementById('regBtn').disabled = isOk == 0;
});