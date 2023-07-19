(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $f1 i32)(local $u i32)(local $a i32)(local $m1 i32)(local $v1 i32)(local $b i32)(local $s1 i32)(local $tmp1 i32)(local $h i32)(local $r i32)(local $p1 i32)(local $k i32)(local $o i32)(local $e i32)(local $l1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 8
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 100
      i32.store
      local.get $a
      local.set $l1
      local.get $l1
      i32.load
      i32.const 0
      i32.eq
      if
        local.get $l1
        i32.const 4
        i32.add
        i32.load
        local.set $h
        local.get $h
        local.set $tmp1
        block $j
          block $t
            block $n
              block $q
                block $Match_tmp1
                  local.get $tmp1
                  i32.const 102
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp1
                  i32.const 100
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  local.get $tmp1
                  i32.const 101
                  i32.eq
                  i32.const 3
                  i32.mul
                  i32.add
                  br_table 3 2 1 0
                end
                i32.const 101
                call $log
                i32.const 0
                local.set $s1
                local.get $s1
                local.set $r
                local.get $r
                local.set $b
                br $j
              end
              i32.const 100
              call $log
              i32.const 0
              local.set $p1
              local.get $p1
              local.set $o
              local.get $o
              local.set $b
              br $j
            end
            i32.const 102
            call $log
            i32.const 0
            local.set $v1
            local.get $v1
            local.set $u
            local.get $u
            local.set $b
            br $j
          end
          i32.const 103
          call $log
          i32.const 0
          local.set $m1
          local.get $m1
          local.set $k
          local.get $k
          local.set $b
        end
      else
        i32.const 103
        call $log
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
  )
  (start $main)
)