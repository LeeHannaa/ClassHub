// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
    // 한 페이지에 보여지는 목록 수 조절 기능 비활성화
    "language": {
      "paginate": {
        "next": "다음",
        "previous": "이전",
        "first": "첫 페이지",
        "last": "마지막 페이지"
      },
      "search": "검색"
    },
    "lengthChange": false,
    "info": false
  });
});
