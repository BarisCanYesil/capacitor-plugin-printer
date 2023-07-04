import { WebPlugin } from '@capacitor/core';

import type { PrinterPlugin } from './definitions';

export class PrinterWeb extends WebPlugin implements PrinterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
