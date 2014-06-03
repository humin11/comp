Highcharts.setOptions(
    global:
      useUTC: false
    lang:
      shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月']
      weekdays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
  )

@initMenu = (url,container) ->
  $.ajax(
    cache:false
    dataType: "json"
    type: "GET"
    url: url
    success: (json) ->
      root = $('#'+container)
      root.empty()
      for node in json
        fetchMenu(node,root)
  )

@fetchMenu = (node,container) ->
  col = $('<li><a href="'+node.url+'"><i class="icon16 fa '+node.icon+'"></i><span class="mm-text">'+node.name+'</span></a></li>')
  if !node.children || node.children.length == 0
    container.append(col)
  else
    col.addClass('mm-dropdown')
    container.append(col)
    ul = $('<ul></ul>')
    col.append(ul)
    for child in node.children
      fetchMenu(child,ul)

@initChart = (url,container,kpi,sub_kpi,title = '',id = '',start_time = '',end_time = '') ->
  $.ajax(
    cache:false
    dataType: "json"
    type: "GET"
    url: url
    data:
      id: id
      title: title
      kpi: kpi
      sub_kpi: sub_kpi
      start_time: start_time
      end_time: end_time
    success: (json) ->
      $('#'+container).empty()
      $('#'+container).highcharts(json)
  )

@initTable = (url,container,model,rowlen = 5,id = '',start_time = '',end_time = '') ->
  $.ajax(
    cache:false
    dataType: "json"
    type: "GET"
    url: url
    data:
      id: id
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
            iDisplayLength: rowlen
            aLengthMenu: [5,10,20,50,100]
          )
        catch error
          #{error}
  )