# bcy-capacitor-plugin-printer

Capacitor plugin for print HTML format value iOS/Android apps.

```typescript
Printer.print({ content: '<b>Lorem ipsum...</b>'})
```

## Install

```bash
npm install bcy-capacitor-plugin-printer
npx cap sync
```

## Usage

```typescript
import { Printer } from 'bcy-capacitor-plugin-printer';
.
.
.

 Printer.print({ content: 'Lorem ipsum...', name: 'lorem-filename', orientation: 'landscape' })
```

## API

<docgen-index>

* [`print(...)`](#print)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### print(...)

```typescript
print(printOptinos: PrintOptions) => Promise<void>
```

| Param              | Type                                                  |
| ------------------ | ----------------------------------------------------- |
| **`printOptinos`** | <code><a href="#printoptions">PrintOptions</a></code> |

--------------------


### Interfaces


#### PrintOptions

| Prop              | Type                | Description                                                 | Default                                                        | Since |
| ----------------- | ------------------- | ----------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`content`**     | <code>string</code> | HTML content for print.                                     |                                                                | 0.0.1 |
| **`name`**        | <code>string</code> | Name of the print of the document.                          | <code>iOS=YourAppName/Android=Document+CurrentTimestamp</code> | 0.0.1 |
| **`orientation`** | <code>string</code> | Orientation of the printing page. "portrait" or "landscape" | <code>"portrait"</code>                                        | 0.0.1 |

</docgen-api>
