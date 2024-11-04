import Foundation
import Capacitor

@objc(PrinterPlugin)
public class PrinterPlugin: CAPPlugin {
    @objc func print(_ call: CAPPluginCall) {
        let content = call.getString("content") ?? ""
        let printController = UIPrintInteractionController.shared
        let jobName = call.getString("name") ?? ""
        let orientation = call.getString("orientation") ?? ""
        if content.starts(with: "base64:") {
            if content.contains("data:") {
                if let base64Index = content.range(of: ",")?.upperBound {
                    let base64String = String(content[base64Index...])
                    
                    if let documentData = Data(base64Encoded: base64String, options: .ignoreUnknownCharacters) {
                        DispatchQueue.main.async {
                            let printInfo = UIPrintInfo(dictionary: nil)
                            printInfo.jobName = jobName
                            printInfo.outputType = .general
                            if orientation == "landscape" {
                                printInfo.orientation = .landscape
                            } else if orientation == "portrait" {
                                printInfo.orientation = .portrait
                            }
                            
                            printController.printInfo = printInfo
                            printController.printingItem = documentData
                            printController.present(animated: true, completionHandler: nil)
                            
                            call.resolve([
                                "message": "success",
                                "value": content,
                                "name": jobName
                            ])
                        }
                        return
                    } else {
                        call.reject("Invalid dataUri data")
                        return
                    }
                } else {
                    call.reject("Invalid dataUri format")
                    return
                }
            } else {
                let base64String = content.replacingOccurrences(of: "base64:", with: "")

                if let documentData = Data(base64Encoded: base64String, options: .ignoreUnknownCharacters) {
                    DispatchQueue.main.async {
                        let printInfo = UIPrintInfo(dictionary: nil)
                        printInfo.jobName = jobName
                        printInfo.outputType = .general
                        if orientation == "landscape" {
                            printInfo.orientation = .landscape
                        } else if orientation == "portrait" {
                            printInfo.orientation = .portrait
                        }
                        
                        printController.printInfo = printInfo
                        printController.printingItem = documentData
                        printController.present(animated: true, completionHandler: nil)
                        
                        call.resolve([
                            "message": "success",
                            "value": content,
                            "name": jobName
                        ])
                    }
                    return
                } else {
                    call.reject("Invalid Base64 data")
                    return
                }
            }
        } else {
            guard let printData = content.data(using: String.Encoding.utf8) else {
                call.reject("Invalid HTML content")
                return
            }
            
            do {
                let printText = try NSAttributedString(data: printData, options: [.documentType: NSAttributedString.DocumentType.html,  .characterEncoding: String.Encoding.utf8.rawValue],  documentAttributes: nil)
            
                DispatchQueue.main.async {
                    let formatter = UISimpleTextPrintFormatter(attributedText: printText)
                    formatter.perPageContentInsets = UIEdgeInsets(top: 50.0, left: 50.0, bottom: 50.0, right: 50.0)
                    let printInfo = UIPrintInfo(dictionary:nil)
                    printInfo.jobName = jobName
                    
                    if orientation == "landscape" {
                        printInfo.orientation = .landscape
                    } else if orientation == "portrait" {
                        printInfo.orientation = .portrait
                    }
                    
                    printController.printInfo = printInfo
                    printController.printFormatter = formatter
                    printController.present(animated: true, completionHandler: nil)
                }
                       
                call.resolve([
                     "message": "success",
                     "value": content,
                     "name": jobName
                 ])
            }
            catch {
                call.reject("Error processing HTML content")
            }
        }
    }
}
