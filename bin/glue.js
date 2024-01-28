// javascript glue for WASM IO
const util = require("util");
const fs = require("fs");
const path = require("path");
const wasmFilePath = process.argv[2]
const wasmBinary = fs.readFileSync(wasmFilePath);
const memory = new WebAssembly.Memory({ initial: 100 });

const imports = {
    system: {
        mem: memory,
        // log raw string
        log_str: function (index, size) {
            let s = "";
            const buffer = new Uint8Array(memory.buffer);
            for (let i = index; i < index + size; ++i)
                s += String.fromCharCode(buffer[i]);
            process.stdout.write(s);
        },
        log_char: function (char) {
            process.stdout.write(String.fromCharCode(char))
        },
        log_int: function (value) {
            process.stdout.write(value.toString())
        }
    }
};

var wasmModule = new Uint8Array(wasmBinary);

WebAssembly.instantiate(wasmModule, imports).then(
    result => result.instance.exports.show(result.instance.exports.main())
)