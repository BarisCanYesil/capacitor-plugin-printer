# capacitor-plugin-printer

Capacitor plugin for printing HTML format value iOS/Android apps.

This plugin supports Capacitor v5.x

```typescript
Printer.print({ content: '<b>Lorem ipsum...</b>'})
```
![screenshoots](https://github.com/BarisCanYesil/capacitor-plugin-printer/assets/17790689/818ea860-f3ba-4d8f-b08a-4df19ec57a43)


### Supported Platforms

- Android 5.1+
- iOS 13+

### Supported Contents

- HTML (can use CSS inline styling)
- Text
- Image(as HTML)

## Install

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

Suggestion for multiple lines:
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
