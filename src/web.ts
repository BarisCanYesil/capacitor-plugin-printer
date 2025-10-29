import { WebPlugin } from '@capacitor/core';

import type { PrinterPlugin } from './definitions';

export class PrinterWeb extends WebPlugin implements PrinterPlugin {
  async print(): Promise<any> {
    console.log('Not supported web browsers!');
    return Promise.reject('Printer plugin is not supported on web.');

  }
}
