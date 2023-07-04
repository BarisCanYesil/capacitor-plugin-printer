export interface PrinterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
