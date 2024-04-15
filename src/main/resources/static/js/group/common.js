$.fn.ajaxDataTable = function (url, data, init, error = null) {
    var setting = {
        "serverSide": true,
        "filter": false,
        "lengthChange": true,
        "paging": true,
        "pagingType": "full_numbers",
        "language": {
            "infoEmpty" : "검색결과가 없습니다.",
            "zeroRecords" : "검색결과가 없습니다.",
            "emptyTable": "검색결과가 없습니다.",
            "lengthMenu": "_MENU_",
        },
        "bInfo": false,
        "bLengthChange": true, // n개씩보기
        "lengthMenu": [[10, 20, 30], ["10개 보기", "20개 보기", "30개 보기"]], // 10/20/30 개씩보기
        "order": [[0, "desc"]],
        "searching": false,
        "autoWidth": false, //가로자동
        "scrollX": "100%",
        "scrollXInner": "100%",
        "ajax": {
            url: url,
            type: "POST",
            data : function(d){
                var tmp = data;
                tmp['pageDraw'] = d.draw;
                tmp['pageStart'] = d.start;
                tmp['pageLength'] = d.length;
                tmp['pageOrder'] = d.order[0].dir; //order[0].column
                return tmp;
            },
            "dataSrc": function (result) {
                if (result.code == 'success') {
                    return result.body;
                } else {
                    alert('통신 실패!!!');
                }
            },
            error: function(xhr, status, err) {
                if (error) {
                    error(status);
                } else {
                    alert('통신 실패!!!');
                }
            }
        },
    };

    var val = $.extend(true, setting, init);
    return $(this).DataTable(val);
};