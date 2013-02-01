REM 获取本机IP地址序列号和MAC地址
'（YH考勤工具）
Function GetSNMAC(ComputerName) 
Dim objWMIService,colItems,objItem,objAddress,MAC,SN,IP
Set objWMIService = GetObject("winmgmts://" & ComputerName & "/root/cimv2")

Set colItems = objWMIService.ExecQuery("Select * From Win32_NetworkAdapterConfiguration Where IPEnabled = True")
For Each objItem in colItems
	 For Each objAddress in objItem.IPAddress
		  If objAddress <> "" then
			  MAC = "MAC地址:" & objItem.MACAddress
			  IP="IP地址:"&objAddress
			  Exit For
		  End If  
	 Next
 Exit For
Next

Set colItems = objWMIService.ExecQuery("Select * From Win32_BaseBoard")
for each item in colItems
	SN="序列号:" & item.SerialNumber
next

Msgbox IP&" "&MAC&" "&SN

End Function

GetSNMAC(".")