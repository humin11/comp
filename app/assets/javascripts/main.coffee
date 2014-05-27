Highcharts.setOptions(
    global:
      useUTC: false
    lang:
      shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月']
      weekdays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
  )

@initchart = (url,container,kpi,sub_kpi,type = 'line',start_time = '',end_time = '') ->
  $.ajax(
    cache:false
    dataType: "json"
    type: "GET"
    url: url
    data:
      type: type
      kpi: kpi
      sub_kpi: sub_kpi
      start_time: start_time
      end_time: end_time
    success: (json) ->
      $('#'+container).empty()
      $('#'+container).highcharts(json)
  )

@inittable = (url,container,model,start_time = '',end_time = '') ->
  $.ajax(
    cache:false
    dataType: "json"
    type: "GET"
    url: url
    data:
      model: model
      start_time: start_time
      end_time: end_time
    success: (json) ->
      $('#'+container).empty()
      try
          table = $('<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered"></table>')
          thead = $('<thead></thead>')
          ttr = $('<tr></tr>')
          tbody = $('<tbody></tbody>')
          ttr.append('<td>'+colname+'</td>') for colname in json.cols
          for row in json.rows
            tr = $('<tr></tr>')
            tr.append('<td>'+obj+'</td>') for obj in row
            tbody.append(tr)
          thead.append(ttr)
          table.append(thead)
          table.append(tbody)
          tableOuterDiv = $('<div class="table-primary" style="margin-bottom: 0"></div>')
          tableOuterDiv.append(table)
          $('#'+container).append(tableOuterDiv)
          table.dataTable(
            bSort: false
            bAutoWidth: false
            iDisplayLength: 5
            aLengthMenu: [5,10,20,50,100]
          )
        catch error
          #{error}
  )