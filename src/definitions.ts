export interface PrinterPlugin {
  print(printOptions: PrintOptions): Promise<void>;
}

export interface PrintOptions {
  /**
 * HTML content for print.
 *
 * @since 0.0.1
 */
  content: string;
  /**
 * Name of the print of the document.
 * @default iOS=YourAppName/Android=Document+CurrentTimestamp
 * @since 0.0.1
 */
  name?: string;
    /**
 * Orientation of the printing page. "portrait" or "landscape"
 * @default "portrait"
 * @since 0.0.1
 */
  orientation?: string;
}
