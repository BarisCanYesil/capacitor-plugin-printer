<h1 align="center">Capacitor Printer Plugin</h1><br>
<p align="center"><strong><code>@bcyesil/capacitor-plugin-printer</code></strong></p>
<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2026?style=for-the-badge" />
  <a href="https://www.npmjs.com/package/@bcyesil/capacitor-plugin-printer"><img src="https://img.shields.io/npm/dw/@bcyesil/capacitor-plugin-printer?style=for-the-badge" /></a>
</p>
<p align="center">
Capacitor plugin for printing HTML, plain text, and images in iOS/Android apps. Supports additional features like Base64 PDF and image printing (from v0.0.5).
</p>
<p align="center">
<img width="175px" height="350px" src="https://github.com/user-attachments/assets/5b638aba-d4ab-43ab-8fbf-a726335ef075">
<img width="400px" height="350px" src="https://github.com/BarisCanYesil/capacitor-plugin-printer/assets/17790689/818ea860-f3ba-4d8f-b08a-4df19ec57a43">
</p>

```typescript
Printer.print({ content: '<b>Lorem ipsum...</b>'})
```

### Versions
| Capacitor Version  | Plugin Version |
| ------------------ | ------------------ |
| v5.x | v0.0.3 |
| v6.x | v0.0.5 |
| v7.x | v0.0.6+ 

### Supported Platforms


| Plugin Version | iOS | Android |
|----------------|-----|----------|
| v0.0.5 and earlier | 13+ | 5.1+ (API 22)|
| v0.0.6+ | 14+ | 6.0+ (API 23) |


### Supported Contents

- HTML (can use CSS inline styling)
- Text
- Image(as HTML)
- Base64(PDF & image) (since v0.0.5)

## Installation
```bash
npm install @bcyesil/capacitor-plugin-printer
npx cap sync
```

## Usage
 ```typescript
import { Printer } from '@bcyesil/capacitor-plugin-printer';

.
.
.

 Printer.print({ content: 'Lorem ipsum...', name: 'lorem-filename', orientation: 'landscape' })
```

## Examples
Text:
```typescript
 Printer.print({ content: 'Lorem ipsum...'})
```

HTML:
```typescript
 Printer.print({ content: '<h1>Lorem</h1>'})
```

Printing multiple HTML elements:
```typescript
    let contentTest: string = "";

    contentTest += '<li style="color:green">Tea</li>'
    contentTest += '<li style="font-size:50px">Coffee</li>'
    contentTest += '<img src="https://picsum.photos/200">'

    Printer.print({ content: contentTest })
```

Images with HTML:
```typescript
 Printer.print({ content: '<img src="base64/url/path">'})
```

with inline CSS:
```typescript
 Printer.print({ content: '<b style="color:red">Lorem ipsum</b>'})
```

Base64:
```typescript
// (since v0.0.5)
// Base64 decoder supports Plain text(just the Base64 value) & Data URI(data:content/type;base64)

 Printer.print({ content: 'base64:...'})
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
print(printOptions: PrintOptions) => Promise<void>
```

| Param              | Type                                                  |
| ------------------ | ----------------------------------------------------- |
| **`printOptions`** | <code><a href="#printoptions">PrintOptions</a></code> |

--------------------


### Interfaces


#### PrintOptions

| Prop              | Type                | Description                                                 | Default                                                        | Since |
| ----------------- | ------------------- | ----------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`content`**     | <code>string</code> | HTML content for print.                                     |                                                                | 0.0.1 |
| **`name`**        | <code>string</code> | Name of the print of the document.                          | <code>iOS=YourAppName/Android=Document+CurrentTimestamp</code> | 0.0.1 |
| **`orientation`** | <code>string</code> | Orientation of the printing page. "portrait" or "landscape" | <code>"portrait"</code>                                        | 0.0.1 |

</docgen-api>
