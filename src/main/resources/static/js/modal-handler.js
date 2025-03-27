document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modal');
    const addRowBtn = document.getElementById('addRowBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmBtn = document.getElementById('confirmBtn');
    const inputName = document.getElementById('inputName');
    const inputDesc = document.getElementById('inputDesc');
    const tableBody = document.getElementById('itemTableBody');

    // 모달 열기
        addRowBtn.addEventListener('click', () => {
        modal.classList.remove('hidden');
        modal.classList.add('flex');
    });

    // 모달 닫기
        cancelBtn.addEventListener('click', () => {
        modal.classList.add('hidden');
        modal.classList.remove('flex');
        inputName.value = '';
        inputDesc.value = '';
    });

    // 확인 버튼 클릭
    confirmBtn.addEventListener('click', () => {
        const name = inputName.value.trim();
        const description = inputDesc.value.trim();

        if (!name || !description) {
            alert('모든 항목을 입력해주세요.');
            return;
        }

        fetch('/items/add', {
            method: 'POST',
            headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `name=${encodeURIComponent(name)}&description=${encodeURIComponent(description)}`
        })
        .then(response => response.json())
        .then(data => {
            // 새 행 추가
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                  <td class="border px-4 py-2">${data.name}</td>
                  <td class="border px-4 py-2">${data.description}</td>
                `;
            tableBody.appendChild(newRow);

            // 모달 닫기 및 초기화
            inputName.value = '';
            inputDesc.value = '';
            modal.classList.add('hidden');
            modal.classList.remove('flex');
        })
        .catch(error => {
            console.error('오류 발생:', error);
            alert('추가 중 오류가 발생했습니다.');
        });
    });
});