import Foundation
import Capacitor

@objc(PrinterPlugin)
public class PrinterPlugin: CAPPlugin {
        @objc func print(_ call: CAPPluginCall) {
            let value = call.getString("content") ?? ""
            let printController = UIPrintInteractionController.shared
            let htmlString = value
            let jobName = call.getString("name") ?? ""
            let orientation = call.getString("orientation") ?? ""
            
            guard let printData = htmlString.data(using: String.Encoding.utf8) else { return }
            do {
                let printText =  try NSAttributedString(data: printData, options: [.documentType: NSAttributedString.DocumentType.html,  .characterEncoding: String.Encoding.utf8.rawValue],  documentAttributes: nil)
            
                DispatchQueue.main.async {
                    let formatter = UISimpleTextPrintFormatter(attributedText: printText)
                    formatter.perPageContentInsets = UIEdgeInsets(top: 50.0, left: 50.0, bottom: 50.0, right: 50.0)
                    let printInfo = UIPrintInfo(dictionary:nil)
                    printInfo.jobName = jobName
                    
                    if(orientation == "landscape") {
                        printInfo.orientation = .landscape
                    }
                    else if(orientation == "portrait"){
                        printInfo.orientation = .portrait
                    }
                    
                    printController.printInfo = printInfo
                    printController.printFormatter = formatter
                    printController.present(animated: true, completionHandler: nil)
                }
                       
                call.resolve([
                     "message":"success",
                     "value": value,
                     "name": jobName
                 ])
            }
            catch {
                call.reject("Error")
            }
        }
}
